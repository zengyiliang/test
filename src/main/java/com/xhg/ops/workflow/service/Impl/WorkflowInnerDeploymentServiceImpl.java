package com.xhg.ops.workflow.service.Impl;

import com.xhg.ops.enums.Status;
import com.xhg.ops.workflow.exception.WorkflowException;
import com.xhg.ops.workflow.service.WorkflowInnerDeploymentService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Service
public class WorkflowInnerDeploymentServiceImpl implements WorkflowInnerDeploymentService {
    protected static final Logger logger = LoggerFactory.getLogger(WorkflowInnerDeploymentServiceImpl.class);


    @Resource
    RepositoryService repositoryService;

    @Override
    public void deployByZipInputStream(String zipFilePath) {

        try {
            DeploymentBuilder builder = repositoryService.createDeployment();
            //获取zip文件的输入流
            FileInputStream fis = new FileInputStream(new File(zipFilePath));
            //读取zip文件，创建ZipInputStream对象
            ZipInputStream zi = new ZipInputStream(fis);
            //添加Zip压缩包资源
            builder.addZipInputStream(zi);
            //执行部署（写入到数据库中）
            //builder.tenantId("");

            builder.deploy();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new WorkflowException(Status.ACTIVITI_ERROR_CODE.getName(),"流程部署出现异常！");

        }

    }


    @Override
    public void deployByClasspath(String bpmnPath,List<String> bpmnFileNameList) {
        /**
        Deployment deployment = repositoryService//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("deployByClasspath")//添加部署名称
                .addClasspathResource(bpmnPath)
                //从classpath的资源中加载，一次只能加载一个文件
                .deploy();//完成部署
        logger.info("部署ID："+deployment.getId());
         **/

        DeploymentBuilder deploymentBuilder = repositoryService//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("deployByClasspath");
        //添加部署名称
            for(String fileName:bpmnFileNameList){
                //从classpath的资源中加载，一次只能加载一个文件
                deploymentBuilder.addClasspathResource(bpmnPath+"/"+fileName);
            }

        //deploymentBuilder.tenantId()
        deploymentBuilder.deploy(); //完成部署
    }

    @Override
    public void deployByInputStream(List<String> bpmnPathList) {
        logger.info("\n\n\n#################------bpmnPathList:"+bpmnPathList);
        try{
            DeploymentBuilder builder = repositoryService.createDeployment();
            for(String bpmnPath : bpmnPathList){
                InputStream inputStream = new FileInputStream(new File(bpmnPath));
                builder.addInputStream(bpmnPath, inputStream);
            }
            // 执行部署方法
            builder.deploy();
        }catch (Exception e){
            e.printStackTrace();
            throw new WorkflowException(Status.ACTIVITI_ERROR_CODE.getName(),"deployByInputStream部署出现异常");
        }

    }

    @Override
    public void deployByInputStream(List<String> bpmnPathList,String tenantId) {
        logger.info("\n\n\n#################------bpmnPathList:"+bpmnPathList+",tenantId:"+tenantId);

        try{
            DeploymentBuilder builder = repositoryService.createDeployment();
            builder.tenantId(tenantId);
            int cnt = 1;
            for(String bpmnPath : bpmnPathList){
                InputStream inputStream = new FileInputStream(new File(bpmnPath));
                builder.addInputStream(bpmnPath, inputStream);
                cnt++;
            }
            // 执行部署方法
            builder.deploy();
        }catch (Exception e){
            e.printStackTrace();
            throw new WorkflowException(Status.ACTIVITI_ERROR_CODE.getName(),"deployByInputStream部署出现异常");
        }


    }

}
