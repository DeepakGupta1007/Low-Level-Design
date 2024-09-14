#include <string>
#include <iostream>

using namespace std;
class Product{
    std::string id;
    public:
    std::string getId(){
        return id;
    }
    void getProduct(std::string pId){
        id=pId;
        std::cout<<"Product Manufactured "+pId<<std::endl;
    }
};

class Payment{
    public:
    void doPayment(Product* product){
        std::cout<<"Payment Done for Product: "+product->getId()<<std::endl;
    }
};

class Invoice{
    public:
    void generateInvoice(Product* product){
        std::cout<<"Invoice generated for Product: "+product->getId()<<std::endl;
    }
};

class NotificationManager{
    public:
    void sendSms(){
        std::cout<<"Product is Ready Take it!!"<<std::endl;
    }
};

class Facade{
    private:
    Product* p;
    Payment* py;
    Invoice* inv;
    NotificationManager* noti;
    public:
    Facade(){
        p= new Product();
        py=new Payment();
        inv=new Invoice();
        noti = new NotificationManager();
    }
    void orderProduct(std::string pId){
        p->getProduct(pId);
        py->doPayment(p);
        inv->generateInvoice(p);
        noti->sendSms();
    }

    ~Facade(){
        delete p;
        delete py;
        delete inv;
        delete noti;
    }
};

int main(){
    Facade* fac=new Facade();

    fac->orderProduct("#RGDG2604");

    delete fac;
}