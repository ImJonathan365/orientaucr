package cr.ac.ucr.orientaucr.orientaucr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cr.ac.ucr.orientaucr.orientaucr.dao_implements.UserDAOImplements;
import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import java.sql.Date;

@SpringBootApplication
public class OrientaucrApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrientaucrApplication.class, args);
                System.out.println("Dilan hola"); 
               
                
	}

}
