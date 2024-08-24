#include <iostream>
class Shape{
    public:
    virtual void draw()=0;
    virtual Shape* clone()=0;
};

class Circle : public Shape{
    void draw() override{
        std::cout<<"Circle Drawn"<<std::endl;
    }
    Shape* clone () override{
        return new Circle(*this);
    }
};

int main(){
    Shape* circle = new Circle();
    circle->draw();
    circle->clone()->draw();


}