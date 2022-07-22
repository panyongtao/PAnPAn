package pan.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pan.Application;

/**
 * @Author pan
 * @Date 2022/7/22 15:13
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
public class TestBcrpt {
    @Test
    public void test(){
        //每次加密都不一样，但每次校验都能通过
        System.out.println(BCrypt.hashpw("123",BCrypt.gensalt()));
        //校验密码
        System.out.println(BCrypt.checkpw("123", "$2a$10$DGXbz.vFJohi9QI50PWy0O7XSvG1eZqBJV8gMt/pI1/7S.hz.D1Ea"));
    }
}
