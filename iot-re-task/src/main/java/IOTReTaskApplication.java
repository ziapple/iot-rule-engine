import com.github.ltsopensource.spring.boot.annotation.EnableTaskTracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Zouping (ziapple@126.com) on 2018.08.24
 */
@SpringBootApplication
@EnableTaskTracker
@ComponentScan("com.casic.iot")
public class IOTReTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(IOTReTaskApplication.class, args);
    }

}
