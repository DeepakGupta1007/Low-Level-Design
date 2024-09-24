//Take the example of DrivingStrategy which might be
//same for two different vehicles.

#include <iostream>
class DriveStrategy{
    public:
    virtual void drive()=0;
};

class NormalDriveStrategy : public DriveStrategy{
    public:
    void drive(){
        std::cout<<"Drive Normally"<<std::endl;
    }
};

class SportDriveStrategy : public DriveStrategy{
    public:
    void drive(){
        std::cout<<"Drive Sporty"<<std::endl;
    }
};


class Vehicle{
    DriveStrategy* driveObject;

    public:
    Vehicle(DriveStrategy* drive){
        driveObject = drive;
    }
    void drive(){
        driveObject->drive();
    }
};



int main(){

    //Creating a vehicle for offroading
    Vehicle* offRoadVehicle = new Vehicle(new SportDriveStrategy());
    offRoadVehicle->drive();

    //Create a normal vehicle
    Vehicle* normalVehicle = new Vehicle(new NormalDriveStrategy());
    normalVehicle->drive();

    //GoodsVehicle
    Vehicle* goodsVehicle = new Vehicle(new NormalDriveStrategy());
    goodsVehicle->drive();
    

}
