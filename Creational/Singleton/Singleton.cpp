#include "Singleton.hpp"

// Definition and initialization of static member variables
Singleton* Singleton::instance = nullptr;
std::mutex Singleton::mutex;
