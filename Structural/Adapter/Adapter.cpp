#include <iostream>
class WeightMachineInterface{
    public:
    virtual int getWeight()=0;
};
class WeightMachineAdaptee: public WeightMachineInterface{
    public:
    int getWeight(){
        return 25; //In Pounds
    }
};

class WeightMachineAdapterInterface{
    public:
    virtual int getWeightInKg()=0;
};
class WeightMachineAdpater: public WeightMachineAdapterInterface{
    WeightMachineInterface* machine;
    public:
    WeightMachineAdpater(WeightMachineInterface* wt){
        machine=wt;
    }
    int getWeightInKg(){
        return 2.5*machine->getWeight();
    }
};

int main(){
    WeightMachineInterface* machine = new WeightMachineAdaptee();
    WeightMachineAdpater* humanMeter= new WeightMachineAdpater(machine);

    std::cout<<humanMeter->getWeightInKg()<<std::endl;

}
