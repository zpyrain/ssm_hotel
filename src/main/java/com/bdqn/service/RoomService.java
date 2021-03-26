package com.bdqn.service;

import com.bdqn.entity.Room;
import com.bdqn.vo.RoomVo;

import java.util.List;

public interface RoomService {

    /**
     * 查询房间列表
     * @param roomVo
     * @return
     */
    List<Room> findRoomListByPage(RoomVo roomVo);
    /**
     * 添加房间
     * @param room
     * @return
     */
    int addRoom(Room room);
    /**
     * 修改房间
     * @param room
     * @return
     */
    int updateRoom(Room room);
    /**
     * 删除房间
     * @param id
     * @return
     */
    int deleteById(int id);
}
