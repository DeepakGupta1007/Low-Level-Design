package Elevtaor;

import java.util.HashSet;
import java.util.Set;

enum Direction {
    UP,
    DOWN,
    IDLE
}

public class Elevator {
    private int currentFloor;
    private Direction direction;
    private Set<Request> requests;
    private int id;

    public Elevator(int id){
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.requests = new HashSet<>();
    }

    public boolean addRequest(Request request){
        if(request.getFloor() < 0 || request.getFloor() >9){
            return false;
        }
        if(request.getFloor()==currentFloor){
            return true;
        }
        if(requests.contains(request)){
            return false;
        }
        return requests.add(request);
    }

    public void step(){
        if(requests.isEmpty()){
            direction = Direction.IDLE;
            return ;
        }

        if(direction == Direction.IDLE) {

            Request nearest = null;
            int minDistance = Integer.MAX_VALUE;

            for (Request req : requests) {
                int distance = Math.abs(currentFloor - req.getFloor());
                if (distance < minDistance) {
                    distance = minDistance;
                    nearest = req;
                }
            }

            direction = (nearest.getFloor() < currentFloor ? Direction.DOWN : Direction.UP);

        }

        RequestType pickUpType = (direction == Direction.UP) ? RequestType.PICKUP_UP : RequestType.PICKUP_DOWN;
        Request pickUpRequest = new Request(currentFloor,pickUpType);
        Request destinatiionRequest = new Request(currentFloor, RequestType.DESTINATION);

        if(requests.contains(pickUpRequest) || requests.contains(destinatiionRequest)){
            requests.remove(pickUpRequest);
            requests.remove(destinatiionRequest);

            if(requests.isEmpty()){
                direction = Direction.IDLE;
            }
            return ;
        }

        if(!hasRequestsAhead(direction)){
            direction = direction == Direction.UP ? Direction.DOWN : Direction.UP;
            return ;
        }

        if(direction == Direction.UP) {
            currentFloor++;

        }
        else{
            currentFloor--;
        }
    }

    private boolean hasRequestsAhead(Direction direction) {
        for (Request request : requests){
            if(direction == Direction.UP && request.getFloor()>currentFloor){
                return true;
            }
            if(direction == Direction.DOWN && request.getFloor()<currentFloor) {
                return true;
            }
        }
        return false;
    }


    public boolean hasRequestsAtOrBeyond(int floor, Direction dir) {
        for (Request request : requests) {
            if (dir == Direction.UP && request.getFloor() >= floor) {
                if (request.getType() == RequestType.PICKUP_UP || request.getType() == RequestType.DESTINATION) {
                    return true;
                }
            }
            if (dir == Direction.DOWN && request.getFloor() <= floor) {
                if (request.getType() == RequestType.PICKUP_DOWN || request.getType() == RequestType.DESTINATION) {
                    return true;
                }
            }
        }
        return false;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }


    public int getId() {
        return id;
    }
}
