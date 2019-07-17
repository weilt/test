package com.demo.test.netty;

import com.alibaba.fastjson.JSON;
import com.demo.test.Enum.ActionEnum;
import com.demo.test.common.Const;
import com.demo.test.common.UpMessage;
import com.demo.test.redis.RedisCache;
import com.demo.test.request.Request;
import com.demo.test.request.updateDBRequest;
import com.demo.test.service.asncservice.RequestAsncService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 处理收到的信息
 */
public class UpHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private RequestAsncService asncService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg = textWebSocketFrame.text();
        try {
            UpMessage message = JSON.parseObject(msg,UpMessage.class);
            Request request = new updateDBRequest(message.getReaderId(),message.getArticleId(),message.getAction());
            //放入异步服务
            asncService.process(request);
            if(message.getAction() == ActionEnum.UP) {
                //针对当前文章的点赞数加1,并获取加1之后的点赞数量
                Long upNumber = RedisCache.incr(Const.ARTICLE_PREFIX + message.getArticleId(), 0);
                message.setNumberOfUp(upNumber);
                ctx.writeAndFlush(message);
            } else if(message.getAction() ==ActionEnum.DELETE){
                Long upNumber = RedisCache.decr(Const.ARTICLE_PREFIX + message.getArticleId(), 0);
                message.setNumberOfUp(upNumber);
                ctx.writeAndFlush(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
