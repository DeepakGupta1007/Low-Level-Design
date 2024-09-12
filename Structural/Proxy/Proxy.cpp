#include <iostream>

//Subject Interface
class Subject {
    public:
    virtual void doSomething()=0;
};

//Real Subject
class RealSubject : public Subject{
    public:
    void doSomething() {
        std::cout<<"Real Subject is performing its operation"<<std::endl;
    }
};

//Proxy 
class Proxy: public Subject{
    private:
    RealSubject* subject;
    bool checkAccess(){
        std::cout<<"Checking Access to Real Object-----"<<std::endl;
        return true;
    }
    void logAccess(){
        std::cout<<"Access Provided to Real Object-----"<<std::endl;
    }
    public:
    Proxy(){
        subject = new RealSubject();
    }
    
    void doSomething(){
        if(checkAccess()){
            logAccess();
            subject->doSomething();
        }
    }
};


int main(){
    //Direct Access
    RealSubject* subject = new RealSubject();
    subject->doSomething();

    //Through proxy
    Proxy* proxy = new Proxy();
    proxy->doSomething();
}

