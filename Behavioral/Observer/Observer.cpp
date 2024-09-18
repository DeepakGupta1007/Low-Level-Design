//Notify Me Design

// NotificationService Interface
#include <iostream>
#include<vector>
class NotificationService{
    public:
    virtual void sendMessage()=0;
};

//SMSNotificationService
class SMSNotificationService : public NotificationService{
    void sendMessage(){
        std::cout<<"Sent a SMS to the following ######7520"<<std::endl;
    }
};

//Mail Notification Service
class MailNotificationService : public NotificationService{
    void sendMessage(){
        std::cout<<"Sent a Mail to the following ######@gmail.com"<<std::endl;
    }
};

class Observer{
    std::vector<NotificationService*> listeners;
    public:
    void add(NotificationService* l){
        listeners.push_back(l);
    }
    void notify(){
        for(int i=0;i<listeners.size();i++){
            listeners[i]->sendMessage();
        }
    }
};

int main(){
    NotificationService* sms = new SMSNotificationService();
    Observer* obs = new Observer();
    obs->add(sms);

    NotificationService* mail = new MailNotificationService();
    obs->add(mail);

    obs->notify();
}
