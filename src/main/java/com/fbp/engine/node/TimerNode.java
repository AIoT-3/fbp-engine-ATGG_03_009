package com.fbp.engine.node;

import com.fbp.engine.core.AbstractNode;
import com.fbp.engine.message.Message;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimerNode extends AbstractNode {
    private final long intervalMs;
    private int tickCount =0;
    ScheduledExecutorService scheduler;

    public TimerNode(String id, long intervalMs) {
        super(id);
        this.intervalMs = intervalMs;
        addOutputPort("out");
    }

    @Override
    public void initialize() {
        super.initialize();

        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {
            try {
                Message msg = new Message(
                        Map.of(
                                "tick", tickCount,
                                "timestamp", System.currentTimeMillis()
                        )
                );

                send("out", msg);

                System.out.println("[" + getId() + "] tick: " + tickCount);

                tickCount++; // ⭐ 증가

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 0, intervalMs, TimeUnit.MILLISECONDS); // ⭐ 핵심
    }


    @Override
    public void shutdown() {
        super.shutdown();
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    @Override
    protected void onProcess(Message message) throws InterruptedException {

    }
}
