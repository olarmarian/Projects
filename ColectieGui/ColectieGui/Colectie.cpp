#include "Colectie.h"

void Colectie::adauga(const TElement& t) {
	abc.adauga(abc.get_root(), t);
	n++;
}

void Colectie::sterge(const TElement& t) {
	if (cauta(t.getNume())) {
		abc.sterge(t);
		n--;
	}
	else {
		throw ColectieException("Medicamentul cautat nu a fost gasit.\n");
	}
}
void Colectie::modifica(const TElement& elem) {
	if (cauta(elem.getNume())) {
		abc.modifica(abc.get_root(),elem);
	}
	else {
		throw ColectieException("Medicamentul cautat nu a fost gasit.\n");
	}
}

bool Colectie::cauta(const std::string& nume) {
	TElement t{ nume,3,"a",3 };
	if (abc.cauta(abc.get_root(), t) != nullptr) {
		return true;
	}
	return false;
}

int Colectie::dim()const {
	return n;
}

bool Colectie::vid()const {
	if (dim() == 0) {
		return true;
	}
	return false;
}


void Iterator::preordine() {
	Node* p = col.abc.get_root();
	Stiva stiva;
	if (p != nullptr) {
		stiva.adauga(p);
	}
	while (!stiva.goala()) {
		p = stiva.scoate();
		elems.adauga(p->data);

		if (p != nullptr) {
			if (p->right != nullptr)
				stiva.adauga(p->right);
			if (p->left != nullptr)
				stiva.adauga(p->left);
		}
	}
}


Iterator Colectie::iterator()const {
	return Iterator(*this);
}

void Iterator::prim() {
	curent = elems.primLista();
}

void Iterator::urmator() {
	curent = curent->urm;
}

bool Iterator::valid()const {
	return curent != NULL;
}

TElement& Iterator::element()const {
	return curent->data;
}