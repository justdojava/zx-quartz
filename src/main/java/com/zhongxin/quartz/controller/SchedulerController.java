package com.zhongxin.quartz.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhongxin.quartz.dao.TaskInformationsDao;
import com.zhongxin.quartz.entity.TaskInformationsEntity;
import com.zhongxin.quartz.service.QuartzService;
import com.zhongxin.quartz.util.Const;
import com.zhongxin.quartz.util.Page;
import com.zhongxin.quartz.vo.TaskInformationsDetailVo;
import com.zhongxin.quartz.vo.TaskInformationsVo;
@Controller
@RequestMapping("/scheduler")
public class SchedulerController extends BaseController{
	
	@Resource
	private QuartzService quartzServer;
	
	@Resource
	private TaskInformationsDao taskInformationsDao;

	/**
	 * 重启指定任务
	 * @param jobKey
	 * @return
	 */
	@RequestMapping("/resumeScheduler/{jobKey}")
	public @ResponseBody String resumeScheduler(@PathVariable String jobKey){
		logger.info("开始重新调度任务");
		String msg = null;
		try {
			msg = quartzServer.resumeScheduler(jobKey);
			logger.info("重新调度任务完成");
			return msg;
		} catch (Exception e) {
			logger.info("重新调度任务异常：", e);
			return msg;
		}
	}
	
	/**
	 * 增加任务调度
	 * @return
	 */
	@RequestMapping("/addScheduler/{jobKey}")
	public @ResponseBody String addScheduler(@PathVariable String jobKey){
		logger.info("增加任务调用开始");
		String msg = null;
		try {
			msg = quartzServer.addScheduler(jobKey);
			logger.info("增加任务调度成功");
			return msg;
		} catch (Exception e) {
			logger.info("异常：",e);
			return msg;
		}
	}
	
	/**
	 * 从定时器中删除指定任务
	 * @param jobKey
	 * @return
	 */
	@RequestMapping("/delScheduler/{jobKey}")
	public @ResponseBody String delScheduler(@PathVariable String jobKey){
		logger.info("删除任务调用开始");
		String msg = null;
		try {
			msg = quartzServer.delScheduler(jobKey);
			logger.info("删除任务调度成功");
			return msg;
		} catch (Exception e) {
			logger.info("异常：",e);
			return msg;
		}
	}
	
	/**
	 * 全部任务重新调度
	 * @return
	 */
	@RequestMapping("/resumeSchedulerAll")
	public @ResponseBody String resumeSchedulerAll(){
		logger.info("开始重新调度任务");
		String msg = null;
		try {
			msg = quartzServer.resumeSchedulerAll();
			logger.info("重新调度任务完成");
			return msg;
		} catch (Exception e) {
			logger.info("重新调度任务异常：", e);
			return msg;
		}
	}
	
	@RequestMapping("/testUrl")
	public @ResponseBody String testUrl(HttpServletRequest request){
		String param = request.getParameter("executeParamter");
		logger.info("http调用成功：" + "-----参数：" +param);
		
		return Const.SUCCESS;
	}
	
	@RequestMapping("/list/{currentPage}")
	public ModelAndView getTaskInformationsList(@PathVariable int currentPage){
		ModelAndView view = new ModelAndView("/page/quartzList.jsp");
		List<TaskInformationsVo> voList = quartzServer.getList(currentPage);
		int count = quartzServer.getTotalCount();
		Page page = new Page(count);
		page.setCurrentPage(currentPage);
		page.setList(voList);
		view.addObject("page",page);
		return view;
	}
	
	@RequestMapping("/detail/{taskNo}")
	public ModelAndView getTaskDetail(@PathVariable String taskNo){
		ModelAndView view = new ModelAndView("/page/quartzDetail.jsp");
		TaskInformationsDetailVo detail = quartzServer.getTaskDetail(taskNo);
		view.addObject("detail",detail);
		return view;
	}
	
	@RequestMapping("/taskEditPage/{id}")
	public ModelAndView taskEditPage(@PathVariable long id){
		ModelAndView view = new ModelAndView("/page/quartzEdit.jsp");
		if(0 != id){
			TaskInformationsEntity entity = quartzServer.selectTaskInfoById(id);
			view.addObject("taskInfo",entity);
			view.addObject("title","编辑定时任务");
		}else{
			view.addObject("title","新建定时任务");
		}
		return view;
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public String editTaskInfo(TaskInformationsEntity entity){
		int count = quartzServer.editTaskInformation(entity);
		if(count == 1){
			return Const.SUCCESS;
		}else if(count == 2){
			return Const.RELOAD;
		}else{
			return Const.FAILED;
		}
	}
	
	@RequestMapping("/executeOnce/{taskNo}")
	@ResponseBody
	public String executeOnce(@PathVariable String taskNo){
		String msg = quartzServer.executeOnce(taskNo);
		return msg;
	}
	
	@RequestMapping("/checkTaskNo/{taskNo}")
	@ResponseBody
	public String checkTaskNo(@PathVariable String taskNo){
		TaskInformationsEntity entity = taskInformationsDao.getTaskByTaskNo(taskNo);
		if(null != entity){
			return Const.FAILED;
		}else{
			return Const.SUCCESS;
		}
	}
	
}
