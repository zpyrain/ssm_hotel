package com.bdqn.service.impl;

import com.bdqn.dao.RoomMapper;
import com.bdqn.entity.Room;
import com.bdqn.service.RoomService;
import com.bdqn.vo.RoomVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomMapper roomMapper;

    /***
     * 查询房间列表
     * @param roomVo
     * @return
     */
    public List<Room> findRoomListByPage(RoomVo roomVo) {
        return roomMapper.findRoomListByPage(roomVo);
    }

    @Override
    public int addRoom(Room room) {
        return roomMapper.addRoom(room);
    }

    @Override
    public int updateRoom(Room room) {
        return roomMapper.updateRoom(room);
    }

    @Override
    public int deleteById(int id) {
        return roomMapper.deleteById(id);
    }
}
