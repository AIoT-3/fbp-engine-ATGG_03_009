package com.fbp.engine.core;

import com.fbp.engine.enums.VisitState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flow {
    private final String id;

    private final Map<String, AbstractNode> nodes = new HashMap<>();
    private final List<Connection> connectionList = new ArrayList<>();

    public Flow(String id) {
        this.id = id;
    }

    public Flow addNode(AbstractNode node){
        // 노드 등록
        nodes.put(node.getId(), node);
        return this;
    }

    public Flow connect(String sourceId, String sourcePort, String targetId, String targetPort){
        // 노드 간 포트 연결
        AbstractNode source = nodes.get(sourceId);
        AbstractNode target = nodes.get(targetId);

        if(source == null || target == null){
            throw new IllegalArgumentException("노드 존재안함");
        }

        OutputPort out = source.getOutputPort(sourcePort);
        InputPort in = source.getInputPort(targetPort);

        if(out == null || in == null) {
            throw new IllegalArgumentException("포트가 존재안함");
        }

        String connId = sourceId + ": " + sourcePort + " > " + targetId + ": " + targetPort;

        Connection connection = new Connection(connId);


        out.connect(connection);
        connection.setTarget(in);

        connectionList.add(connection);

        return this;
    }

    public void initialize(){
        // 모든 노드 실행
        if(nodes.isEmpty()){
            throw new IndexOutOfBoundsException("노드 없음");
        }
        nodes.values().forEach(AbstractNode::initialize);
    }

    public void shutdown(){
        // 모든 노드 종료
        nodes.values().forEach(AbstractNode::shutdown);
    }

    public List<String> validate(){
        // 구조 검증 및 사이클 검사
        List<String> errors = new ArrayList<>();

        if(nodes.isEmpty()){
            errors.add("Flow에 노드없음");
            return errors;
        }

        Map<String, List<String>> stringListMap = new HashMap<>();

        for(String nodeId : nodes.keySet()){
            stringListMap.put(nodeId, new ArrayList<>());
        }

        for(Connection connection : connectionList){
            String id = connection.getId();

            try{
//                String[] parts = id.split(">");
//                String[] src = parts[0].split(":");
//                String[] tgt = parts[1].split(":");


//                String sourceId = src[0];
//                String sourcePort = src[1];
//                String targetId = tgt[0];
//                String targetPort = tgt[1];

//                AbstractNode source = nodes.get(sourceId);
//                AbstractNode target = nodes.get(targetId);
//
//                if (source == null){
//                    errors.add("source 존재안함: " + sourceId);
//                }else if(source.getOutputPort(sourcePort)==null){
//                    errors.add("sourceport 존재안함: "+ sourcePort);
//                }
//
//                if(target == null){
//                    errors.add("target 노드 존재안함:" +targetId);
//                }else if(target.getOutputPort(targetPort) == null){
//                    errors.add("targetPort 존재안함: "+ targetPort);
//                }


                String[] parts = id.split(">");
                String src = parts[0].split(":")[0];
                String tgt = parts[1].split(":")[0];

                String sourceId = src;
                String sourcePort = src;
                String targetId = tgt;
                String targetPort = tgt;

                if(!nodes.containsKey(sourceId)){
                    errors.add("존재하지 않는 sourceId: "+ sourceId);
                }else if(!nodes.containsKey(targetId)){
                    errors.add("존재하지 않는 targetId: " + targetId);
                }

                if(!nodes.containsKey(sourcePort)){
                    errors.add("존재하지 않는 sourcePort: " + sourcePort);
                }else if(!nodes.containsKey(targetPort)){
                    errors.add("존재하지 않는 targetPort: " + targetPort);
                }

                stringListMap.get(sourceId).add(targetId);
            } catch (Exception e) {
                errors.add("connection id 잘못됨: "+id);
            }
        }

        Map<String, VisitState> state = new HashMap<>();

        for(String node : nodes.keySet()){
            state.put(node, VisitState.UNVISITED);
        }

        for(String node : nodes.keySet()){
            if(state.get(node) == VisitState.UNVISITED){
                if(dfs(node, stringListMap , state)){
                    errors.add("순환 참조");
                    break;
                }
            }
        }

        return errors;
    }


    private boolean dfs(String node,
                        Map<String, List<String>> graph,
                        Map<String, VisitState> state) {

        state.put(node, VisitState.VISITING);

        for (String next : graph.get(node)) {

            if (state.get(next) == VisitState.VISITING) {
                return true;
            }

            if (state.get(next) == VisitState.UNVISITED) {
                if (dfs(next, graph, state)) { // 자기를 다시 호출
                    return true;
                }
            }
        }

        state.put(node, VisitState.VISITED);
        return false;
    }

    public Map<String, AbstractNode> getNodes(){
        return nodes;
    }

    public List<Connection> getConnectionList(){
        return connectionList;
    }
}
