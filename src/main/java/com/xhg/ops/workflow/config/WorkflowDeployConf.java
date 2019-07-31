package com.xhg.ops.workflow.config;

import com.xhg.ops.workflow.model.ProcessInfo;
import com.xhg.ops.workflow.service.WorkflowInnerDeploymentService;
import com.xhg.ops.workflow.utils.ActivitiUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class WorkflowDeployConf {

    protected static final Logger logger = LoggerFactory.getLogger(WorkflowDeployConf.class);


    private Resource[] deploymentResources;

    @Autowired
    ProcessConfiguration processConfiguration;

    @Autowired
    SpringProcessEngineConfiguration springProcessEngineConfiguration;

    @Value("${xhg.activiti.process-definitioin-path}")
    private  String workflowProcessLocation; //流程定义的路径

    @Autowired
    WorkflowInnerDeploymentService workflowInnerDeploymentService;

    @Autowired
    RepositoryService repositoryService;

    @Value("${xhg.activiti.use.tenant}")
    private boolean useTenant;




    /**
     * 利用构造方法初始化变量
     * @throws Exception
     */
    public WorkflowDeployConf() throws Exception{
        //ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //deploymentResources = resolver.getResources("classpath:/com/pccw/solutions/diagram/*.bpmn");
        //在构造函数中不能够获取
        //logger.info("\n\n ---------------WorkflowDeployConf-------------------");

       // deploying();
    }

    /**
     * 部署流程资源文件数据
     * @throws Exception
     */
    @PostConstruct
    public  void deploying() throws Exception {
        logger.info("\n\n>>>>>>>>>>>>>>>>>>>deploying() - 流程资源部署开始");

        logger.info("\n\n deploying useTenant:"+useTenant);
        logger.info("workflowProcessLocation:"+workflowProcessLocation);

        if(StringUtils.isEmpty(workflowProcessLocation)){
            logger.error("\n\n xhg.activiti.process-definitioin-path 为空");
        }

        List<File> dirList = getProcessDir();

        logger.info("dirList:"+dirList);

        String[] extensions = new String[]{"bpmn"};

        //如果目录不为空
        if(CollectionUtils.isNotEmpty(dirList)){
            for(File dir : dirList){
                logger.info("dir.getName():"+ dir.getName());
                Collection<File> fileCollection = FileUtils.listFiles(dir,extensions,false);
                logger.info("fileCollection:"+ fileCollection);

                if( CollectionUtils.isNotEmpty(fileCollection) ){
                    List<String> bpmnPathList = new ArrayList<>();
                    for(File item:fileCollection){
                        bpmnPathList.add(item.getAbsolutePath());
                    }

                    //logger.info("bpmnPathList:"+ bpmnPathList);

                    //直接部署，不进行判断
                    /**
                    if(useTenant){
                        this.workflowInnerDeploymentService.deployByInputStream(bpmnPathList,dir.getName());
                    }else{
                        this.workflowInnerDeploymentService.deployByInputStream(bpmnPathList);
                    }
                     **/
                   // this.workflowInnerDeploymentService.deployByInputStream(bpmnPathList,dir.getName());



                    //部署时进行判断
                    this.judgeExistDeploy(fileCollection,dir.getName());


                }
            }
        }

        logger.info("\n\n<<<<<<<<<<<<<<<<<<<<<<<< deploying() - 流程资源部署结束");
    }

    /**
     * 部署时需要进行判断
     *
     * 根据目录下的文件和数据库中已经存在的进行文件对比（checksumCRC32）
     * 如果没有改变，则不部署；否则进行部署
     *
     * @throws IOException
     */
    private void judgeExistDeployBackup() throws IOException {
        if (deploymentResources != null) {
            ProcessEngine engine = springProcessEngineConfiguration.buildProcessEngine();
            RepositoryService repositoryService = engine.getRepositoryService();

            for (Resource r : deploymentResources) {
                String deploymentName = r.getFilename();
                boolean doDeploy = true;
                //List deployments =

                List<Deployment> deployments = repositoryService
                        .createDeploymentQuery().deploymentName(deploymentName)
                        .orderByDeploymenTime().desc().list();

                if (!deployments.isEmpty()) {
                    Deployment existing = deployments.get(0);
                    try {
                        InputStream in = repositoryService.getResourceAsStream(
                                existing.getId(), deploymentName);
                        if (in != null) {
                            File f = File.createTempFile("deployment", "bpmn",
                                    new File(System.getProperty("java.io.tmpdir")));

                            f.deleteOnExit();
                            OutputStream out = new FileOutputStream(f);
                            IOUtils.copy(in, out);
                            in.close();
                            out.close();
                            //r是准备部署的
                            //f是已经存在的

                            logger.info("FileUtils.checksumCRC32(f):"+ FileUtils.checksumCRC32(f));
                            logger.info("FileUtils.checksumCRC32(r.getFile()):"+FileUtils.checksumCRC32(r.getFile()));

                            doDeploy = (FileUtils.checksumCRC32(f) != FileUtils.checksumCRC32(r.getFile()));
                        }
                        else {
                            throw new ActivitiException("不能读取资源 " + deploymentName + ", 输入流为空");
                        }
                    }
                    catch (ActivitiException ex) {
                        logger.error("Unable to read " + deploymentName
                                + " of deployment " + existing.getName()
                                + ", id: " + existing.getId()
                                + ", will re-deploy");
                    }
                }

                /**
                if (doDeploy) {
                    DeploymentBuilder deployment = repositoryService.createDeployment();
                    FileInputStream pngStream = new FileInputStream(r.getFile().toString().replace(".bpmn", ".png"));
                    deployment.name(deploymentName).addInputStream(deploymentName, r.getInputStream());
                    deployment.name(deploymentName).addInputStream(deploymentName.replace(".bpmn", ".png"), pngStream);
                    //deployment.deploy();
                }
                **/
            }
        }
    }



    private void judgeExistDeploy(Collection<File> fileCollection,String tenantId) throws IOException {
            List<String> bpmnPathList = new ArrayList<>();

            for (File resource : fileCollection) {
                //需要从bpmn的xml文件中解析出流程key
                String deploymentName = resource.getName();
                String filePathAndName = resource.getAbsolutePath();

                //logger.info("\n\n===========================deploymentName:"+deploymentName);

                //logger.info("\n\n===========================filePathAndName:"+filePathAndName);

                BpmnModel bpmnModel = ActivitiUtil.getBpmnModelFromFile(filePathAndName);
                ProcessInfo processInfo =ActivitiUtil.parseBpmnModelForProcessInfo(bpmnModel);

                String processDefinitionKey = processInfo.getId();//
                //logger.info("\n\n===========================processDefinitionKey:"+processDefinitionKey);

                //


                //是否需要部署
                boolean doDeploy = true;


                /**
                List<Deployment> deployments = repositoryService
                        .createDeploymentQuery()
                        //.processDefinitionKey("xhgVacationProcess")
                        .deploymentName("xhgVacationProcess")
                        .orderByDeploymenTime().desc().list();
                */

                //查询数据库中是否存在 流程定义key 相同的  流程定义
                List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                        .processDefinitionKey(processDefinitionKey)
                        //.processDefinitionTenantId()
                        .orderByProcessDefinitionVersion().desc().list();

                if( CollectionUtils.isEmpty(processDefinitions)  ){ //如果数据库中不存在，则需要部署
                    doDeploy = true;
                }else{
                    //如果数据库中存在，则需要比较 数据库最新的部署文件  和 当前准备部署文件 的 checksumCRC32，
                    // 如果相同，则不需要部署；如果不相同，则需要部署
                   // logger.info("\n\n processDefinitions:"+processDefinitions);
                    ProcessDefinition existProcessDef  = processDefinitions.get(0); //获取最新的流程
                    //logger.info("\n\n existProcessDef:"+existProcessDef);

                    String resourceName = existProcessDef.getResourceName();
                    String diagramResourceName = existProcessDef.getDiagramResourceName();
                    //logger.info("\n---resourceName:"+resourceName+",diagramResourceName:"+diagramResourceName);

                    InputStream inputStream = repositoryService.getResourceAsStream(existProcessDef.getDeploymentId(),
                            resourceName);

                    if(null != inputStream){
                        doDeploy = this.compareDeployFile(inputStream,resource,deploymentName);
                    }else{
                        doDeploy = true;
                    }

                }


                if (doDeploy) {
                    bpmnPathList.add(resource.getAbsolutePath());
                }

            }

            if(CollectionUtils.isNotEmpty(bpmnPathList)){
                if(useTenant){
                    this.workflowInnerDeploymentService.deployByInputStream(bpmnPathList,tenantId);
                }else{
                    this.workflowInnerDeploymentService.deployByInputStream(bpmnPathList);
                }
            }
            //
    }


    /**
     * 比较部署文件是否相同
     * @param in
     * @param resource
     * @param deploymentName
     * @return
     * @throws IOException
     */
    private boolean compareDeployFile(InputStream in,File resource, String deploymentName) throws IOException {
        logger.info("compareDeployFile resource:"+resource);
        logger.info("compareDeployFile deploymentName:"+deploymentName);

        boolean doDeploy = true;
        try {
            if (in != null) {
                File f = File.createTempFile("deployment", "bpmn",
                        new File(System.getProperty("java.io.tmpdir")));
                f.deleteOnExit();
               // logger.info("\n---自动生成的文件:"+ f.getAbsolutePath()+","+f.getName());
                OutputStream out = new FileOutputStream(f);
                IOUtils.copy(in, out);
                in.close();
                out.close();

                //logger.info("\n---FileUtils.checksumCRC32(f):"+ FileUtils.checksumCRC32(f));
                //logger.info("\n---FileUtils.checksumCRC32(r.getFile()):"+FileUtils.checksumCRC32(resource));

                if(FileUtils.checksumCRC32(f) != FileUtils.checksumCRC32(resource)){
                    doDeploy = true;
                }else{
                    doDeploy = false;
                }

                //处理后删除
                f.delete();
            }
            else {
                throw new ActivitiException("不能读取资源 " + deploymentName + ", 输入流为空");
            }
        }
        catch (ActivitiException ex) {
            logger.error("Unable to read " + deploymentName);
            throw new ActivitiException("Unable to read " + deploymentName);
        }

        return  doDeploy;
    }


    /**
     * 获取流程目录
     * @return
     */
    private List<File> getProcessDir() {
        String processPath = ActivitiUtil.getWorkflowProcessPath(workflowProcessLocation);
        //logger.info("processPath:"+processPath);
        File file = new File(processPath);
        File[] tempList = file.listFiles();
       // logger.info("tempList:"+tempList);
        List<File> dirList = new ArrayList<>();
        for (File item:tempList) {
            //logger.info("item:"+item);
            if (item.isDirectory()) {
              //  logger.info("文件夹：" + item);
                dirList.add(item);
            }
        }
        return dirList;
    }

}
