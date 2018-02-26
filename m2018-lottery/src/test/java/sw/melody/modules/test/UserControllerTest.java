package sw.melody.modules.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebAppConfiguration
@SpringApplicationConfiguration(classes = MockServletContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup().build();
    }


    @Test
    public void getUserList() throws Exception {
        RequestBuilder request = null;
        request = get("/user/");
        //1.读取用户列表
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));

        //2.post提交一个user
        request = post("/user/").param("id", "1").param("name", "wangping").param("age", "17");
        mvc.perform(request).andExpect(content().string(equalTo("success")));

        //3.测试post提交的用户
        request = get("/user/");
        //1.读取用户列表
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"wangping\",\"age\":17}]")));

        //4.测试put修改用户
        request = put("/user/1").param("id", "1").param("name", "sufen").param("age", "18");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));

        //5.测试get读取一个用户
        request = get("/user/1");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("{\"id\":1,\"name\":\"sufen\",\"age\":18}")));

        //6.测试delete删除一个用户
        request = delete("/user/1");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("success"));

        //7.测试结果
        request = get("/user/");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("[]"));
    }

    @Test
    public void postUser() {
    }

    @Test
    public void putUser() {
    }

    @Test
    public void putUser1() {
    }

    @Test
    public void deleteUser() {
    }
}