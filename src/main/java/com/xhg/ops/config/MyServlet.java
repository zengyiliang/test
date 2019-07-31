package com.xhg.ops.config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MyServlet", urlPatterns = {"/myServlet"})
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        try {
            printWriter.write("<head>");
            printWriter.write("<title>MyFirstServlet</title>");
            printWriter.write("</head>");
            printWriter.write("<body>");
            printWriter.write("<h2>Servlet MyFirstServlet at 1234544</h2>");
            printWriter.write("<h3> Welcome " +username +" come in servlet <h3/>");
            printWriter.write("</body>");
            printWriter.write("</html>");
            response.flushBuffer();
        }finally {
            printWriter.close();
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }
    @Override
    public String getServletInfo() {
        return "MyServlet";
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("初始化Sevlet============。。。。。。。。。。。。。。。");
    }

    /**
     * import org.springframework.boot.web.servlet.ServletComponentScan;
     *
     * @SpringBootApplication(scanBasePackages = {"com.xhg"})
     * @EnableCaching(proxyTargetClass = true) // 开启缓存功能
     * @EnableAsync
     * @MapperScan({"com.xhg.**.dao","com.xhg.**.mapper"})
     * @ServletComponentScan(value = {"com.xhg"})主要是要添加这个
     */
}