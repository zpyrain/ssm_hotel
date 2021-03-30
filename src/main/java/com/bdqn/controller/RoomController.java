package com.bdqn.controller;

import com.bdqn.entity.Room;
import com.bdqn.entity.RoomType;
import com.bdqn.service.RoomService;
import com.bdqn.service.RoomTypeService;
import com.bdqn.vo.RoomVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Resource
    private RoomService roomService;
    @Resource
    private RoomTypeService roomTypeService;

    /**
     * 查询房间的详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/{id}.html")  //id 可以在requestMapping里面占位
    public String detail(@PathVariable Integer id, Model model){
           //调用查询房间详情的方法
        Room room = roomService.findById(id);
        //无刷新的用json  非无刷新的用model
        model.addAttribute("room",room);
        return "detail";
    }

    /**
     * 查询全部房间列表
     * @param model
     * @return
     */
    @RequestMapping("/list.html")
    public String list(Model model){
        //创建查询条件类
        RoomVo roomVo = new RoomVo();
        roomVo.setStatus(3);//可预订
        //查询房间列表
        List<Room> roomList = roomService.findRoomListByPage(roomVo);
        //调用查询所有房型列表的方法
        List<RoomType> roomTypeList = roomTypeService.findRoomTypeList(null);
        model.addAttribute("roomTypeList",roomTypeList);
        model.addAttribute("roomList",roomList);
        return "hotelList";
    }
    /**
     * 查询全部房间列表
     * @param model
     * @return
     */
    @RequestMapping("/list/{id}")
    public String list(@PathVariable Integer id,Model model){

        //创建查询条件类
        RoomVo roomVo = new RoomVo();
        roomVo.setStatus(3);//可预订
        roomVo.setRoomtypeid(id);
        //调用查询所有房型列表的方法
        List<RoomType> roomTypeList = roomTypeService.findRoomTypeList(null);
        //查询房间列表
        List<Room> roomList = roomService.findRoomListByPage(roomVo);
        model.addAttribute("roomTypeList",roomTypeList);
        model.addAttribute("roomList",roomList);
        model.addAttribute("typeId",id);//将当前选中的房型ID保存到模型中，目的是在页面中回显选中的文本(改变选中的颜色)
        return "hotelList";
    }
}
