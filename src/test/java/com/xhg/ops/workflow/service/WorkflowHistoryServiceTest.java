package com.xhg.ops.workflow.service;

import com.xhg.ops.OpsApplication;
import com.xhg.ops.workflow.utils.FastJsonUtils;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程历史方面的查询
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OpsApplication.class)
public class WorkflowHistoryServiceTest {
    //https://blog.csdn.net/zjx86320/article/details/50363544

    @Autowired
    ProcessEngine processEngine;

    private String processDefinitionId = "vacationProcess:9:27504";

    String processInstanceId = "9ac7723d-9332-11e8-a471-54e1adcdbcf6";

    /**查询历史流程实例*/
    @Test
    public void findHisProcessInstance() {
        //查找按照某个流程定义的规则一共执行了多少次流程
        List<HistoricProcessInstance> list = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .processDefinitionId(processDefinitionId)//流程定义ID
                .list();

        if (list != null && list.size() > 0) {
            for (HistoricProcessInstance hi : list) {
                System.out.println(hi.getId() + "	  " + hi.getStartTime() + "   " + hi.getEndTime());
            }
        }

    }




    /**查询历史活动
     * 问题：HistoricActivityInstance对应哪个表
     * 问题：HistoricActivityInstance和HistoricTaskInstance有什么区别*/
    @Test
    public void findHisActivitiList(){
        //String processInstanceId = "1801";

        /**

         select RES.* from ACT_HI_ACTINST RES WHERE RES.PROC_INST_ID_ = ? order by RES.ID_ asc LIMIT ? OFFSET ?


         */
        //查询某一次流程的执行一共经历了多少个活动
        List<HistoricActivityInstance> list = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if(list != null && list.size()>0){
            for(HistoricActivityInstance hai : list){
               // System.out.println("hai.getActivityType():"+hai.getActivityType()+" "+hai.getId()+"  "+hai.getActivityName());
                System.out.println(FastJsonUtils.toJSONString(hai));
            }
        }
    }



    /**查询历史任务
     * 问题：HistoricTaskInstance对应哪个表*/
    @Test
    public void findHisTaskList(){
        //String processInstanceId = "1801";

        /**

         elect distinct RES.* from ACT_HI_TASKINST RES WHERE RES.PROC_INST_ID_ = ?
         order by RES.ID_ asc LIMIT ? OFFSET ?

         */
        ///查询摸一次流程的执行一共经历了多少个任务
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if(list!=null && list.size()>0){
            for(HistoricTaskInstance hti:list){
                //System.out.println(hti.getId()+"    "+hti.getName()+"   "+hti.getClaimTime());
                System.out.println(FastJsonUtils.toJSONString(hti));
            }
        }
    }



    /**查询历史流程变量*/
    @Test
    public void findHisVariablesList(){
       // String processInstanceId = "1801";
        //查询某一次流程的执行一共设置的流程变量
        /**

         select RES.* from ACT_HI_VARINST RES WHERE RES.PROC_INST_ID_ = ? order by RES.ID_ asc LIMIT ? OFFSET ?

         */
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if(list != null && list.size()>0){
            for(HistoricVariableInstance hvi:list){
                System.out.println(hvi.getId()+"    "+hvi.getTaskId()+" "+hvi.getVariableName()+"	"+hvi.getValue());

                //System.out.println(FastJsonUtils.toJSONString(hvi));
            }
        }
    }

    @Test
    public void getVariableValues(){
        TaskService taskService=processEngine.getTaskService(); // 任务Service
        String taskId="1182521";

        Map<String, Object> varMap =  taskService.getVariables(taskId);

        System.out.println("varMap："+varMap);
    }

    @Test
    public void getComments(){
        //select * from ACT_HI_COMMENT where PROC_INST_ID_ = ? order by TIME_ desc
        List<Comment> list = processEngine.getTaskService().getProcessInstanceComments(processInstanceId);
        System.out.println("list："+list);
        for(Comment com:list){
            System.out.println("ID:"+com.getId());
            System.out.println("Message:"+com.getFullMessage());
            System.out.println("TaskId:"+com.getTaskId());
            System.out.println("ProcessInstanceId:"+com.getProcessInstanceId());
            System.out.println("UserId:"+com.getUserId());
        }

    }



    @Test
    public void findHisActAndVar(){
        //String processInstanceId = "1801";

        /**

         select RES.* from ACT_HI_ACTINST RES WHERE RES.PROC_INST_ID_ = ? order by RES.ID_ asc LIMIT ? OFFSET ?


         */
        //查询某一次流程的执行一共经历了多少个活动
        List<HistoricActivityInstance> actInstancelist = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();

        List<HistoricVariableInstance> varlist = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();



        if(actInstancelist != null && actInstancelist.size()>0){
            for(HistoricActivityInstance hai : actInstancelist){
                 System.out.print("hai.getActivityType():"+hai.getActivityType()+",getId:"+hai.getId()+" ,getActivityName:"+hai.getActivityName());
                //System.out.println(FastJsonUtils.toJSONString(hai));

                Map<String,Object> taskParam = new HashMap<String,Object>();

                if( StringUtils.isNotEmpty( hai.getTaskId() ) && null != varlist && varlist.size()>=0){

                    for(HistoricVariableInstance varItem:varlist){
                        if(StringUtils.isNotEmpty(varItem.getTaskId()) && varItem.getTaskId().equals(hai.getTaskId())){
                            taskParam.put(varItem.getVariableName(),varItem.getValue());
                        }
                    }
                }

                System.out.println(",taskParam:"+taskParam);

            }
        }
    }




}
