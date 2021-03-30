package com.bdqn.dao;

import com.bdqn.entity.Room;
import com.bdqn.vo.RoomVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomMapper {
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

    /**
     * 根据楼层查询房间列表
     * @return
     */
    List<Room> findRoomListByFloorId();

    /**
     * 查看房间详情
     * @param id
     * @return
     */
    Room findById(Integer id);
}
