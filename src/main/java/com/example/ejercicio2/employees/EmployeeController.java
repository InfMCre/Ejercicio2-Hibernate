package com.example.ejercicio2.employees;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.ejercicio2.departments.model.Department;
import com.example.ejercicio2.departments.model.DepartmentServiceModel;
import com.example.ejercicio2.departments.repository.DepartmentRepository;
import com.example.ejercicio2.employees.model.Employee;
import com.example.ejercicio2.employees.model.EmployeePostRequest;
import com.example.ejercicio2.employees.model.EmployeeServiceModel;
import com.example.ejercicio2.employees.model.EmployeesExpands;
import com.example.ejercicio2.employees.model.TestPostRequest;
import com.example.ejercicio2.employees.repository.EmployeeRepository;

@RequestMapping("api")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;

	
	@GetMapping("/employees")
	public ResponseEntity<Iterable<Employee>> getEmployees() {
		return new ResponseEntity<Iterable<Employee>>(employeeRepository.findAll(), HttpStatus.OK);
	}
	
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<EmployeeServiceModel> getEmployeeById(
			@PathVariable("id") Integer id,
			@RequestParam(required = false) List<EmployeesExpands> expand
	) {
		
		Employee employee = employeeRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No encontrado")
		);

		// TODO tampoco debería estar aqui. los iniciamos vacios y los seteamos abajo
		DepartmentServiceModel departmentResponse = null;
		EmployeeServiceModel bossResponse = null;
		// y cargamos los datos de necesitarlos.
		if (expand != null && expand.indexOf(EmployeesExpands.BOSS) != -1) {
			Employee boss = employee.getBoss();
			bossResponse = new EmployeeServiceModel(
				boss.getId(),
				boss.getName(),
				boss.getPosition(),
				boss.getSalary(),
				null,
				boss.getBossId(),
				null,
				boss.getDepartmentId()
			);
		}
		if (expand != null && expand.indexOf(EmployeesExpands.DEPARTMENT) != -1) {
			Department department = employee.getDepartment();
			departmentResponse = new DepartmentServiceModel(
				department.getId(),
				department.getName(),
				department.getCity()
			);
		}
		
		EmployeeServiceModel response = new EmployeeServiceModel(
				employee.getId(),
				employee.getName(),
				employee.getPosition(),
				employee.getSalary(),
				bossResponse, // o nulo o cargado
				employee.getBossId(),
				departmentResponse, // o nulo o cargado
				employee.getDepartmentId()
		);
		
		return new ResponseEntity<EmployeeServiceModel>(response, HttpStatus.OK); 
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Integer> deleteEmployee(@PathVariable("id") Integer id) {
		try {
			employeeRepository.deleteById(id);
			return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe");
		}
	}
	

	@PostMapping("/employees")
	public ResponseEntity<EmployeeServiceModel> createEmployee(@RequestBody EmployeePostRequest employeePostRequest) {
		
		// TODO esta parte logica deberia estar en el servicio
		// cargamos el boss y el departamento de la DB
		Employee boss = employeeRepository.findById(employeePostRequest.getBossId()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.CONFLICT, "Jefe no encontrado")
		);
		Department department = departmentRepository.findById(employeePostRequest.getDepartmentId()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.CONFLICT, "Departamento no  encontrado")
		);
		
		Employee employee = new Employee(
			employeePostRequest.getName(), 
			employeePostRequest.getPosition(), 
			employeePostRequest.getSalary(), 
			boss, 
			department
		);
		employee = employeeRepository.save(employee);
		EmployeeServiceModel response = new EmployeeServiceModel(
			employee.getId(),
			employee.getName(),
			employee.getPosition(),
			employee.getSalary(),
			null, // o nulo o cargado. pero habría que llamar a la funcion para cargarlo
			employee.getBossId(),
			null, // o nulo o cargado. pero habría que llamar a la funcion para cargarlo.
			employee.getDepartmentId()
		);
		return new ResponseEntity<EmployeeServiceModel>(response, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<EmployeeServiceModel> updateEmployee(@PathVariable("id") Integer id, @RequestBody EmployeePostRequest employeePostRequest) {
		
		Employee employee = employeeRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.CONFLICT, "Empleado no encontrado")
		);
		
		// TODO 
		// de esta forma evitaremos poner nulos si no se envian
		// hacer asi con todos
		if (employeePostRequest.getName() != null) {
			employee.setName(employeePostRequest.getName());
		}
		
		
		employee = employeeRepository.save(employee);
		EmployeeServiceModel response = new EmployeeServiceModel(
			employee.getId(),
			employee.getName(),
			employee.getPosition(),
			employee.getSalary(),
			null, // o nulo o cargado. pero habría que llamar a la funcion para cargarlo
			employee.getBossId(),
			null, // o nulo o cargado. pero habría que llamar a la funcion para cargarlo.
			employee.getDepartmentId()
		);
		
		return new ResponseEntity<EmployeeServiceModel>(response, HttpStatus.OK);
	}	
	
	
	@PostMapping("/employees/images")
	public ResponseEntity<EmployeeServiceModel> testImages(@RequestBody TestPostRequest employeePostRequest) throws IOException {
		EmployeeServiceModel response = new EmployeeServiceModel();
		
		String fileName = "prueba11.png";
		String [] fileAsStrings = employeePostRequest.getFile().split(",");
		
		byte[] imageBytes = Base64.getDecoder().decode(fileAsStrings[1]);
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
		
		String mimeType = extractMimeType(employeePostRequest.getFile());
		System.out.println(mimeType);
		
		File outputFile = new File("src/main/resources/static/images/avatars/" + fileName);
		ImageIO.write(bufferedImage, mimeType.split("/")[1], outputFile);

		return new ResponseEntity<EmployeeServiceModel>(response, HttpStatus.OK);
	}
	

	@GetMapping("/employees/images")
	public ResponseEntity<TestPostRequest> testImagesGet() throws IOException {
		TestPostRequest response = new TestPostRequest();
		
		String fileName = "prueba11.png";
		String filePath = "src/main/resources/static/images/avatars/" + fileName;
		/*
		// ok
		File file = new File(filePath);
		String extension = FilenameUtils.getExtension(filePath);
		BufferedImage bufferedImage = ImageIO.read(file);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, extension, byteArrayOutputStream );
		byte [] data = byteArrayOutputStream.toByteArray();
		
		String image = "data:image/" + extension + ";base64," + Base64Utils.encodeToString(data);
		response.setFile(image);
		*/
		
		File file = new File(filePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        response.setFile(Base64.getEncoder().encodeToString(fileContent));
        //https://stackoverflow.com/questions/27886677/javascript-get-extension-from-base64-image
		
	
		return new ResponseEntity<TestPostRequest>(response, HttpStatus.OK);
	}
	
	private static String extractMimeType(final String encoded) {
	    final Pattern mime = Pattern.compile("^data:([a-zA-Z0-9]+/[a-zA-Z0-9]+).*,.*");
	    final Matcher matcher = mime.matcher(encoded);
	    if (!matcher.find())
	        return "";
	    return matcher.group(1).toLowerCase();
	}
	/*
    public static byte[] toByteArray(BufferedImage bi, String format)
        throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }
    */
}