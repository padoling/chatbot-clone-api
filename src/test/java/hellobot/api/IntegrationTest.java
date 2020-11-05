package hellobot.api;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Ignore
public class IntegrationTest {

}
