#include <string>
#include <iostream>
#include <vector>
using namespace std;
class Student{
    std::string name;
    int age;
    std::string gender;
    std::vector<std::string> subjects;
    int rollnumber;
    public:
    Student(std::string name,std::string gender,int age,int rollnumber,vector<string> s){
        this->name = name;
        this->age=age;
        this->rollnumber = rollnumber;
        this->subjects=s;
        this->gender=gender;
    }
    std::string toString(){
        std::string result = " roll number: " + to_string(rollnumber) +
                " age: " + to_string(age) +
                " name: " + name +
                " subjects: " + subjects[0] ;
        return result;
    }
};
class StudentBuilder{
    public:
    std::string name;
    int age;
    std::string gender;
    std::vector<std::string> subjects;
    int rollnumber;
    StudentBuilder* setRollNumber(int r){
        rollnumber = r;
        return this;
    }
    StudentBuilder* setName(std::string n){
        name =n;
        return this;
    }
    StudentBuilder* setAge(int r){
        age = r;
        return this;
    }
    StudentBuilder* setGender(std::string gender){
        this->gender = gender;
        return this;
    }
    virtual StudentBuilder* setSubjects()=0;

    Student* build(){
        return new Student(name,gender,age,rollnumber,subjects);
    }

};
class EngineeringStudentBuilder : public StudentBuilder{
    public:
    StudentBuilder* setSubjects(){
        subjects.push_back("Compiler Design,TAFL,DSA,PPS");
        return this;
    }
};
class MedicalStudentBuilder : public StudentBuilder{
    public:
    StudentBuilder* setSubjects(){
        subjects .push_back("Biology,Zoology,Botany,Chemistry");
        return this;
    }
};


class Director {

    StudentBuilder* studentBuilder;

    public:
    Director(StudentBuilder* studentBuilder){
        this->studentBuilder = studentBuilder;
    }

    Student* createStudent(){
        if(dynamic_cast<EngineeringStudentBuilder*>(studentBuilder)){
            return createEngineeringStudent();
        }
        else if(dynamic_cast<MedicalStudentBuilder*>(studentBuilder)){
            return createMBAStudent();
        }
        return nullptr;
    }

    private:
    Student* createEngineeringStudent(){

        return studentBuilder->setRollNumber(1)->setAge(22)->setName("Deepak")->setSubjects()->build();
    }

    Student* createMBAStudent(){

        return studentBuilder->setRollNumber(1)->setAge(22)->setName("Radhika")->setSubjects()->build();
    }
};

int main(){
    Director* director=new Director(new EngineeringStudentBuilder());
    Student* student = director->createStudent();
     std::cout<<student->toString();

   

}