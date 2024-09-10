#include <iostream>

class BreathImplementor{
    public:
    virtual void breathe()=0;
};

class LandBreathImplementor:public BreathImplementor{
    void breathe(){
        std::cout<<"LandBreathImplementor called!";
    }
};

class WaterBreathImplementor:public BreathImplementor{
    void breathe(){
        std::cout<<"WaterBreathImplementor called!";
    }
};

class TreeBreathImplementor:public BreathImplementor{
    void breathe(){
        std::cout<<"TreeBreathImplementor called!";
    }
};

class LivingThings{
    

    public:
    BreathImplementor* implementor;
    virtual void breathProcess()=0;
};

class Dog : public LivingThings{
    public:
    Dog(BreathImplementor* imp){
        implementor=imp;
    }

    void breathProcess(){
        implementor->breathe();
    }

};

class Tree : public LivingThings{
    public:
    Tree(BreathImplementor* imp){
        implementor=imp;
    }

    void breathProcess(){
        implementor->breathe();
    }
};

class Fish : public LivingThings{
    public:
    Fish(BreathImplementor* imp){
        implementor=imp;
    }

    void breathProcess(){
        implementor->breathe();
    }
};

int main(){
    BreathImplementor* implementor = new WaterBreathImplementor();
    Fish* fish = new Fish(implementor);
    fish->breathProcess();
}

