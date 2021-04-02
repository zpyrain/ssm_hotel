package com.bdqn.service.impl;

import com.bdqn.dao.OrdersMapper;
import com.bdqn.dao.RoomMapper;
import com.bdqn.dao.RoomTypeMapper;
import com.bdqn.entity.Orders;
import com.bdqn.entity.Room;
import com.bdqn.entity.RoomType;
import com.bdqn.service.OrdersService;
import com.bdqn.utils.UUIDUtils;
import com.bdqn.vo.OrdersVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private RoomTypeMapper roomTypeMapper;

    /**
     * 添加订单
     * @param orders
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)//触发异常时回滚
    public int addOrders(Orders orders) {
        orders.setStatus(1);//1-待确认
        orders.setOrdersno(UUIDUtils.randomUUID());
        orders.setReservedate(new Date());//预定时间为当前系统时间
        int count = ordersMapper.addOrders(orders);
        //判断订单是否添加成功，添加成功操作房间及房型
        if(count>0){
            //修改房间状态为已预订(状态码为1)
            //查询房间信息
            Room room = roomMapper.findById(orders.getRoomid());
            room.setStatus(1);//修改房间状态为已预订(编号1)
            //调用修改房间信息的方法
            roomMapper.updateRoom(room);

            //修改房型(可用房间数-1，已预订数量+1)
            RoomType roomType = roomTypeMapper.findById(orders.getRoomtypeid());
            //修改可用房间数量
            roomType.setAvilablenum(roomType.getAvilablenum()-1);
            //修改已预订数量
            roomType.setReservednum(roomType.getReservednum()+1);
            //调用修改房型的方法
            roomTypeMapper.updateRoomType(roomType);
        }

        return count;
    }

    @Override
    public List<Orders> findOrdersList(OrdersVo ordersVo) {
        return ordersMapper.findOrdersList(ordersVo);
    }

    @Override
    public int updateOrders(Orders orders) {
        return ordersMapper.updateOrders(orders);
    }
}
