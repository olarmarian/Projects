#pragma once
#include <iostream>
#include <string>

class Medicament {
private:
	std::string nume;
	int pret;
	int stoc;
	std::string producator;
public:
	Medicament() {}
	Medicament(const std::string& n, int p, const std::string& prod, int s) :nume{ n }, pret{ p }, producator{ prod }, stoc{ s } {}

	const void setPret(int p) {
		pret = p;
	}
	
	const void setProd(const std::string p) {
		producator=p;
	}

	std::string getNume()const {
		return nume;
	}
	std::string getProducator()const {
		return producator;
	}
	int getPret() const {
		return pret;
	}
	int getStoc() const {
		return stoc;
	}

	bool operator==(const Medicament& m1) {
		return m1.getNume() == this->nume;
	}

	bool operator>= (Medicament m1) {
		return m1.getNume() >= this->nume;
	}
	bool operator> (Medicament m1) {
		return m1.getNume() > this->nume;
	}
	bool operator<=(Medicament& m1) {
		return m1.getNume() <= this->nume;
	}
	bool operator<(Medicament m1) {
		return m1.getNume() < this->nume;
	}
	bool operator!=(Medicament& m1) {
		return m1.getNume() != this->nume;
	}
	void operator=(Medicament m1) {
		this->nume = m1.getNume();
		this->producator = m1.getProducator();
		this->pret = m1.getPret();
		this->stoc = m1.getStoc();
	}
	Medicament& operator()() {
		return *this;
	}
	friend std::ostream& operator<<(std::ostream& out, const Medicament& med) {
		out << med.getNume() << " " << med.getPret() << " " << med.getProducator();
		return out;
	}
	
};