#include <string>
#include <iostream>

class Pizza {
public:
    std::string description;

    virtual int getPrice() = 0;
    virtual std::string getDescription() {
        return description;
    }

    virtual ~Pizza() {
        
    } // Destructor for base class
};

class BasePizza : public Pizza {
public:
    BasePizza() {
        description = "A delicious pizza";
    }

    int getPrice()  {
        return 125;
    }
};

// Decorator Interface
class ExtraToppings : public Pizza {
protected:
    Pizza* pizza;
public:
    ExtraToppings(Pizza* p) : pizza(p) {}

    virtual ~ExtraToppings() { // Virtual destructor to handle cleanup
        delete pizza;
    }
};

class Cheese : public ExtraToppings {
public:
    Cheese(Pizza* p) : ExtraToppings(p) {}

    std::string getDescription()  {
        return pizza->getDescription() + ", Cheese";
    }

    int getPrice()  {
        return 20 + pizza->getPrice();
    }
};

class Paneer : public ExtraToppings {
public:
    Paneer(Pizza* p) : ExtraToppings(p) {}

    std::string getDescription()  {
        return pizza->getDescription() + ", Paneer";
    }

    int getPrice()  {
        return 30 + pizza->getPrice();
    }
};

class Mushroom : public ExtraToppings {
public:
    Mushroom(Pizza* p) : ExtraToppings(p) {}

    std::string getDescription()  {
        return pizza->getDescription() + ", Mushroom";
    }

    int getPrice()  {
        return 40 + pizza->getPrice();
    }
};

class Chili : public ExtraToppings {
public:
    Chili(Pizza* p) : ExtraToppings(p) {}

    std::string getDescription()  {
        return pizza->getDescription() + ", Chili";
    }

    int getPrice()  {
        return 10 + pizza->getPrice();
    }
};

class Onion : public ExtraToppings {
public:
    Onion(Pizza* p) : ExtraToppings(p) {}

    std::string getDescription()  {
        return pizza->getDescription() + ", Onion";
    }

    int getPrice()  {
        return 10 + pizza->getPrice();
    }
};

int main() {
    // Paneer and onion Radhika's fav
    Pizza* pizza = new Onion(new Paneer(new BasePizza()));

    std::cout << pizza->getDescription() << " having cost " << pizza->getPrice() << std::endl;

    delete pizza; // Properly delete the pizza object to avoid memory leak
    return 0;
}
