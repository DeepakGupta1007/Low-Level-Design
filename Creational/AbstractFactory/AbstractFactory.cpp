#include <iostream>
using namespace std;

class Pizza {
    public:
    virtual void bake()=0;
    virtual void dispatch()=0;
};

class PaneerIndianPizza : public Pizza{
    public:
    void bake(){
        std::cout<<"PaneerIndianPizza baked"<<std::endl;
    }
    void dispatch(){
        std::cout<<"PaneerIndianPizza dispatched"<<std::endl;
    }
};
class OnionIndianPizza : public Pizza{
    public:
    void bake(){
        std::cout<<"OnionIndianPizza baked"<<std::endl;
    }
    void dispatch(){
        std::cout<<"OnionIndianPizza dispatched"<<std::endl;
    }

};

class PaneerRussianPizza: public Pizza{
    void bake(){
        std::cout<<"PaneerRussianPizza baked"<<std::endl;
    }
    void dispatch(){
        std::cout<<"PaneerRussianPizza dispatched"<<std::endl;
    }

};
class OnionRussianPizza : public Pizza{
    void bake(){
        std::cout<<"OnionRussianPizza baked"<<std::endl;
    }
    void dispatch(){
        std::cout<<"OnionRussianPizza dispatched"<<std::endl;
    }
};
enum PizzaType{
    Onion,
    Paneer
};

enum Country{
    India,
    Russia
};

class PizzaFactory{
    public:
    virtual Pizza* getPizza(PizzaType type)=0;
};

class IndianPizzaFactory : public PizzaFactory{
    Pizza* getPizza(PizzaType type) override{
        if(type == PizzaType::Onion){
            return new OnionIndianPizza();
        }
        else if(type == PizzaType::Paneer){
            return new PaneerIndianPizza;
        }
    }
};

class RussianPizzaFactory : public PizzaFactory{
    Pizza* getPizza(PizzaType type) override{
        if(type == PizzaType::Onion){
            return new OnionRussianPizza();
        }
        else if(type == PizzaType::Paneer){
            return new PaneerRussianPizza;
        }
    }
};


class CountryFactory{
    public:
    PizzaFactory* getPizzaFactory(Country name){
        if(name == Country::India){
            return new IndianPizzaFactory();
        }
        else if(name == Country::Russia){
            return new RussianPizzaFactory();
        }
        return nullptr;
    }
};


int main(){
    CountryFactory* factory = new CountryFactory();
    PizzaFactory* pizzafactory = factory->getPizzaFactory(India);

    Pizza* pizza = pizzafactory->getPizza(Onion);
    pizza->bake();
    pizza->dispatch();

    return 0;


}