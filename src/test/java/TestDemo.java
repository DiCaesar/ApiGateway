import com.r2d2.api.core.ApiGatewayHand;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * Created by DiCaesar on 2017/11/14
 */
@Slf4j
public class TestDemo extends BaseTest {

    @Autowired
    private ApiGatewayHand apiGatewayHand;

    @Test
    public void test() throws Exception{
        log.info("========================start...{}","sss");
        Thread.sleep(TimeUnit.SECONDS.toMillis(10000));
    }

}
