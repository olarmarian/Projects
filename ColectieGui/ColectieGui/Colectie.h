#pragma once
#include "TElement.h"
#include "Arbore.h"
#include "Util.h"
class Iterator;


class ColectieException {
	std::string message;
public:
	ColectieException(const std::string& msg) :message{ msg }{}
	const std::string& getMessage()const {
		return message;
	}
};

class Colectie {
private:
	int n;
	Arbore& abc;
public:
	friend class Iterator;

	Colectie(Arbore& ab) :abc{ ab } {
		n = 0;
	}
	void adauga(const TElement& elem);
	void sterge(const TElement& elem);
	void modifica(const TElement& elem);
	bool cauta(const std::string& nume);
	int dim()const;
	bool vid()const;
	Iterator iterator()const;
	
};

class Iterator {
private:
	const Colectie& col;
	NodLista* curent;
	Lista elems;
	void preordine();
public:
	friend class Colectie;
	Iterator(const Colectie& colectie) :col{ colectie } {
		preordine();
		curent = elems.primLista();
	}
	friend class Colectie;
	void prim();
	void urmator();
	bool valid() const;
	TElement& element() const;
};