//Flyweight Interface

#include <string>
#include <iostream>
#include <unordered_map>
class Character{
    public:
    virtual void print(std::string font)=0;
};

//Flyweight Concrete class
class CharacterConcrete : public Character{
    char charecter;
    public:
    CharacterConcrete(char ch){
        charecter=ch;
    }
    void print(std::string font) {
        std::cout<<"Charecter: "<<charecter<<" . Font:"<<font<<std::endl;
    }
};

//Flyweight Factory
class CharecterFactory{
    std::unordered_map<char,CharacterConcrete*> mp;

    public:
    CharacterConcrete* getCharacter(char ch){
        if(mp.find(ch)==mp.end()){
            std::cout<<"Creating character "<<ch<<std::endl;
            mp[ch] = new CharacterConcrete(ch);
        }
        return mp[ch];
    }
};

int main(){
    CharecterFactory* factory = new CharecterFactory();
    CharacterConcrete* ch1 = factory->getCharacter('a');
    ch1->print("Helvetiva");

    CharacterConcrete* ch2= factory->getCharacter('b');
    ch2->print("Roboto");

    CharacterConcrete* ch3= factory->getCharacter('a');
    ch3->print("Bold");

    


}