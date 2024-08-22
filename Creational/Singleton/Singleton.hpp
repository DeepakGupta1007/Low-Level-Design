#ifndef SINGLETON_H
#define SINGLETON_H

#include <iostream>
#include <mutex>

class Singleton {
private:
    static Singleton* instance;
    static std::mutex mutex;

    // Private constructor to prevent instancing
    Singleton() {
        std::cout << "Singleton instance created" << std::endl;
    }

    // Delete copy constructor and assignment operator
    Singleton(const Singleton&) = delete;
    Singleton& operator=(const Singleton&) = delete;

public:
    // Static method to provide access to the single instance
    static Singleton* getInstance() {
        std::lock_guard<std::mutex> lock(mutex); // Ensure thread-safety
        if (instance == nullptr) {
            instance = new Singleton();
        }
        return instance;
    }

    // Method to demonstrate functionality
    void someBusinessLogic() {
        std::cout << "Doing some business logic" << std::endl;
    }

    // Static method to delete the instance
    static void deleteInstance() {
        std::lock_guard<std::mutex> lock(mutex);
        if (instance != nullptr) {
            delete instance;
            instance = nullptr;
            std::cout << "Singleton instance destroyed" << std::endl;
        }
    }

    // Destructor
    ~Singleton() {
        // Destructor code, if any
    }
};

#endif // SINGLETON_H
