#include <string>
#include <iostream>

class Animal {
    public:
    std::string color;

    virtual void setColor(std::string c)=0;
    virtual std::string getColor()=0;
    
};

class Dog : public Animal{
    void setColor(std::string c){
        color = c;
    }
    std::string getColor(){
        return color;
    }
};

class Cat : public Animal{
    void setColor(std::string c){
        color = c;
    }
    std::string getColor(){
        return color;
    }
};
enum Type {
    DOG,
    CAT,
    NONE
} ;
class AnimalFactory{
    public:
    Animal* getAnimal(Type t){
        if(t == DOG){
            return new Dog();
        }
        else if(t==CAT){
            return new Cat();
        }
        else{
            std::cout<<"Not avaiulable in factory";
            return nullptr;
        }
    }
};

int main(){
    AnimalFactory* factory = new AnimalFactory();

    Animal* dog= factory->getAnimal(DOG);
    Animal* cat= factory->getAnimal(CAT);
    Animal* none = factory->getAnimal(NONE);

    if(dog){
        dog->setColor("Black");
    }
    if(cat){
        cat->setColor("Pink");
    }
    if(none){
        none->setColor("None");
    }

    std::cout<<"Dog Color "<<dog->getColor();

    return 0;

}