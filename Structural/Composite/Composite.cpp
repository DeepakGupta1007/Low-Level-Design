#include <string>
#include <iostream>
#include <vector>
class FileSystem{
    public:
    virtual void ls()=0;
};

class File : public FileSystem{
    std::string name ;
    public:
    File(std::string name){
        this->name = name;
    }
    void ls(){
        std::cout<<"File Name : "+name<<std::endl;
    }
};

class Directory: public FileSystem{
    std::vector<FileSystem*> dir;
    public:
    void insertFileSystem(FileSystem* data){
        dir.push_back(data);
    }
    void ls(){
        for(int i = 0 ; i < dir.size() ; i++ ){
            dir[i]->ls();
        }
    }

};

int main(){
    File* file1 = new File("Deepak");
    File* file2 = new File("Radhika");

    Directory* dir1 = new Directory();
    dir1->insertFileSystem(file1);
    dir1->insertFileSystem(file2);

    File* file3 = new File("Vanshika");

    Directory* dir = new Directory();
    dir->insertFileSystem(dir1);

    dir->insertFileSystem(file3);


    dir->ls();
}