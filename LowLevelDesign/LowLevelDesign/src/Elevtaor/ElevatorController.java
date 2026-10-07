package Elevtaor;

import java.util.ArrayList;
import java.util.List;

public class ElevatorController {

    private List<Elevator> elevators;

    public ElevatorController(){
        elevators = new ArrayList<>();
        elevators.add(new Elevator(0));
        elevators.add(new Elevator(1));
        elevators.add(new Elevator(2));
    }

    public boolean requestElevator (int floor, RequestType type){
        if(floor<0 || floor>9) return false;
        if(type == RequestType.DESTINATION){
            return false;
        }
        Request request = new Request(floor,type);
        Elevator best = selectBestElevator(request);
        if (best == null) return false;
        return best.addRequest(request);
    }

    private Elevator selectBestElevator(Request request) {
        Elevator best = findCommittedToFloor(request);
        if(best != null){
            return best;
        }

        best = findNearestIdle(request.getFloor());
        if(best!=null){
            return best;
        }

        return findNearest(request.getFloor());
    }

    private Elevator findNearestIdle(int floor) {
        Elevator nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            if (e.getDirection() != Direction.IDLE) {
                continue;
            }

            int distance = Math.abs(e.getCurrentFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = e;
            }
        }

        return nearest;
    }

    private Elevator findCommittedToFloor(Request request) {
        int floor = request.getFloor();
        Direction direction = (request.getType() == RequestType.PICKUP_UP ? Direction.UP : Direction.DOWN);

        Elevator nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for(Elevator elevator : elevators){
            if(elevator.getDirection() != direction){
                continue;
            }
            if((direction == Direction.UP && elevator.getCurrentFloor()>floor) ||
            direction == Direction.DOWN && elevator.getCurrentFloor()<floor){
                continue;
            }

            if(!elevator.hasRequestsAtOrBeyond(floor,direction)){
                continue;
            }

            int distance = Math.abs(elevator.getCurrentFloor() -floor);
            if(distance < minDistance){
                minDistance = distance;
                nearest = elevator;
            }

        }
        return nearest;
    }

    private Elevator findNearest(int floor) {
        Elevator nearest = elevators.get(0);
        int minDistance = Math.abs(elevators.get(0).getCurrentFloor() - floor);

        for (Elevator e : elevators) {
            int distance = Math.abs(e.getCurrentFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = e;
            }
        }

        return nearest;
    }

    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }

    public void printStatus() {
        for (Elevator elevator : elevators) {
            System.out.println(
                    "Elevtaor.Elevator " + elevator.getId() +
                            " | Floor: " + elevator.getCurrentFloor() +
                            " | Elevtaor.Direction: " + elevator.getDirection()
            );
        }
    }

}
