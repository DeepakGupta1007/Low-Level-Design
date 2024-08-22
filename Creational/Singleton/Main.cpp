#include "Singleton.hpp"

int main() {
    Singleton* s1 = Singleton::getInstance();
    s1->someBusinessLogic();

    Singleton* s2 = Singleton::getInstance();
    s2->someBusinessLogic();

    // Both instances should be the same
    std::cout << "s1 and s2 are " << (s1 == s2 ? "the same" : "different") << std::endl;

    // Explicitly delete the Singleton instance
    Singleton::deleteInstance();

    return 0;
}
