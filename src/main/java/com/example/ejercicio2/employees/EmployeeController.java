package com.example.ejercicio2.employees;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public ResponseEntity<EmployeeServiceModel> testImages(@RequestBody TestPostRequest employeePostRequest) 
			throws IOException {
		// aqui creariamos el modelo de respuesta, yo lo he creado en un controller de employee... 
		// para crear el ejemplo
		EmployeeServiceModel response = new EmployeeServiceModel();

		String imageString = employeePostRequest.getFile();
		// **** deberia hacerse toda la lógica en el SERVICE
		// POdemos: pedir siempre una extension en concreto (forzarlo).
		// sacar la extension del contenido con la siguiente función detectMimeType
		// (https://stackoverflow.com/questions/57976898/how-to-get-mime-type-from-base-64-string),
		// podemos pedir al usuario que incluya un campo con la extension y/o nombre completo del fichero.
		// pero sería tan sencillo como pedir un campo mas en el TestPostRequest que es mi caso de ejemplo
		
		// generamos la ruta del archivo donde vamos a guardar
		// lo voy a guardar dentro de src pero no se deberían guardar aquí las imagenes subidas por un usuario. 
		// Para este proyecto nos vale por que es sencillo,
		// pero cuidado estas imagenes acabarán en el git...
		String extensionArchivo = detectMimeType(imageString);
		String fileName = "prueba32" + extensionArchivo; // aqui el nombre esta hardcodeado...
		String outputFile = "src/main/resources/static/images/avatars/" + fileName;
		
		// convertimos el String del fichero a bytes
		byte[] decodedImg = Base64.getDecoder().decode(imageString.getBytes(StandardCharsets.UTF_8));
		// guardamos la imagen con las dos siguientes lineas
		Path destinationFile = Paths.get(outputFile);
		Files.write(destinationFile, decodedImg);
		
		// devolvemos lo que queramos. aunque sea un 200
		return new ResponseEntity<EmployeeServiceModel>(response, HttpStatus.OK);
	}
	
	// el tipo de archivo está definido en la primera posición del string en base64
	// (https://stackoverflow.com/questions/57976898/how-to-get-mime-type-from-base-64-string)
	private String detectMimeType(String base64Content) {
		HashMap<String, String> signatures = new HashMap<String, String>();
		signatures.put("JVBERi0", ".pdf");
		signatures.put("R0lGODdh", ".gif");
		signatures.put("R0lGODdh", ".gif");
		signatures.put("iVBORw0KGgo", ".png");
		signatures.put("/9j/", ".pdf");
		String response = ""; 
		
		for(Map.Entry<String, String> entry : signatures.entrySet()) {
		    String key = entry.getKey();
		    if (base64Content.indexOf(key) == 0) {
		    	response = entry.getValue();
		    }
		}
		return response;
	}
	
	
	@GetMapping("/employees/images")
	public ResponseEntity<TestPostRequest> testImagesGet() throws IOException {
		TestPostRequest response = new TestPostRequest();
		
		// conseguimos la ruta del archivo. 
		// Esto si son avatares podría ser una ruta fija en función de la ID de usuario
		// o mejor, guardaríamos la ruta en la BBDD en un campo, y la obtendríamos de ahi.
		String fileName = "prueba32.png";
		String filePath = "src/main/resources/static/images/avatars/" + fileName;
		
		File file = new File(filePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        response.setFile(Base64.getEncoder().encodeToString(fileContent));
	
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