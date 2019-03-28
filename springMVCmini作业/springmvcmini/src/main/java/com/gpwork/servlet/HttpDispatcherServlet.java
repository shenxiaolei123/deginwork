package com.gpwork.servlet;

import com.gpwork.annotations.SLAutowired;
import com.gpwork.annotations.SLController;
import com.gpwork.annotations.SLParam;
import com.gpwork.annotations.SLRequestMapping;
import org.apache.jasper.servlet.JspServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpDispatcherServlet extends HttpServlet {
    //key = url,value = 请求执行的方法
    private Map<String, Method> urlmapping = new HashMap<String,Method>();
    //key=classname,value=class实例 calss对应的类
    private Map<String, Object> classmapping = new HashMap<String,Object>();
    private JspServlet jspServlet = null;
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("初始化");
        InputStream inputStream =
        config.getClass().getClassLoader().getResourceAsStream("/");
        URL url = this.getClass().getClassLoader().getResource("");
        System.out.println(url.getFile());
        try {
            doScanFile(url.getFile());
            System.out.println(classmapping);
            //下一下设置类中的field字段的值
            for(Map.Entry<String,Object> entry:classmapping.entrySet()){
                Field fields[] = entry.getValue().getClass().getDeclaredFields();
                System.out.println(fields.length+"--"+entry.getValue());
                for(Field field : fields){
                    SLAutowired slAutowired = (SLAutowired)field.getAnnotation(SLAutowired.class);
                    if(slAutowired!=null){
                        System.out.println(field.getType().getName());
                        Object objf = classmapping.get(field.getType().getName());
                        System.out.println("类中字段的值:"+objf);
                        field.setAccessible(true);
                        field.set(entry.getValue(),objf);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        for(Map.Entry<String,Object> entry:classmapping.entrySet()){
            Object obj = entry.getValue();
            Class clazz = obj.getClass();
            SLController annotation = (SLController) clazz.getAnnotation(SLController.class);
            if(annotation!=null){
                System.out.println("controller");
                Method methods[] = clazz.getMethods();
                for(Method method:methods){
                   SLRequestMapping slRequestMapping =  method.getAnnotation(SLRequestMapping.class);
                   if(slRequestMapping!=null){
                       System.out.println("有映射url");
                       urlmapping.put(slRequestMapping.value(),method);
                   }
                }
            }
        }
        //jsp初始化
        jspServlet = new JspServlet();
        jspServlet.init(config);
    }

    private void doScanFile(String path) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        File file = new File(path);
        if(file.isFile()){
            System.out.println("文件:"+file.getName());
            return;
        }
        String filelistname[] = file.list();
        for(String filename : filelistname){
            File f = new File(path+"/"+filename);
            if(f.isFile()){
                System.out.println("文件:"+f.getName());
                String abspath = f.getAbsolutePath();
                String pn = abspath.substring(abspath.lastIndexOf("\\classes\\")+8,abspath.length());
                String n = pn.replaceFirst("[\\\\]","").replaceAll("[\\\\]",".");
                if(n.endsWith("class")){
                    //class文件
                    n = n.replaceAll(".class","");
                    Class aClass = this.getClass().getClassLoader().loadClass(n);
                    if(aClass.isAnnotation()) continue;
                    classmapping.put(n,aClass.newInstance());
                }
            }else{
                doScanFile(path+"/"+filename);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {this.doPost(req,resp);}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception " + Arrays.toString(e.getStackTrace()));
        }
    }
    private void doDispatch(HttpServletRequest request,HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, ServletException, IOException {
       String url = request.getRequestURI();
       String contextpath = request.getContextPath();
       url = url.replaceAll("/+","/");
       Method method = urlmapping.get(url);
       try{
           String result = null;
          if(method!=null) {

             Map<String,String[]> parammap = request.getParameterMap();
             Class paramtypes[] =  method.getParameterTypes();
              Object paramsmethod[] = new Object[paramtypes.length];
             Annotation [][] annotations = method.getParameterAnnotations();
             for(int i=0;i<paramtypes.length;i++){
                 Class param = paramtypes[i];
                if(param==Integer.class) ;
                else{
                    if(annotations[i][0] instanceof SLParam){
                        SLParam param1 = (SLParam) annotations[i][0];
                        //只能取相同参数的一个值
                        String pv = request.getParameter(param1.value());
                        //能取相同参数的多个值
                        if(pv!=null)
                         pv = Arrays.asList(parammap.get(param1.value())).stream().map(String::toUpperCase).collect(Collectors.joining(","));
                        paramsmethod[i] = pv;
                    }
                }
             }
              result = (String) method.invoke(classmapping.get(method.getDeclaringClass().getName()), paramsmethod);
          }else{
              boolean b = resouceHandler(request,response);
              if(b){
                  return;
              }
          }
          response.setCharacterEncoding("utf-8");
           response.setContentType("text/html; charset=utf-8");
          response.getWriter().println(result);
       }catch (Exception e){
           e.printStackTrace();
       }

    }
    //其它资源处理
    private boolean resouceHandler(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String url = request.getRequestURI();
        String contextpath = request.getContextPath();
        File f = getUrlfile(request,url);
        if (!f.exists()) {
            response.setStatus(404);
            response.sendRedirect("/404.html");
            return true;
        }
        if(url.endsWith(".css")||url.endsWith(".ico")||url.endsWith(".html")) {
            System.out.println(url+"文件存在");
            //request.getRequestDispatcher(url).forward(request,response);
            InputStream inputStream = new FileInputStream(f);
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
            return true;
        }else if(url.endsWith(".jsp")){
            System.out.println("jsp文件存在");
            try {
                jspServlet.service(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    private File getUrlfile(HttpServletRequest request,String url){
        String path = request.getSession().getServletContext().getRealPath(url);
        System.out.println(path);
        File f = new File(path);
        return f;
    }
}
