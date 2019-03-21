#pragma once
#include <iostream>
#include "TElement.h"
#include "Arbore.h"

struct NodLista {
	TElement data;
	NodLista* urm;
};

class Lista {
private:
	NodLista * prim;
	NodLista * ultim;
	int n;
public:
	Lista() {
		prim = nullptr;
		ultim = nullptr;
		n = 0;
	}
	NodLista* primLista() {
		return prim;
	}
	void adauga(const TElement& t) {
		NodLista* temp = new NodLista{ t,nullptr };
		if (prim == nullptr) {
			prim = temp;
			ultim = temp;
		}
		else {
			ultim->urm = temp;
			ultim = temp;
		}
		n++;
	}

	void printList() {
		NodLista* curent = prim;
		while (curent) {
			std::cout << "[ " << curent->data << " ]" << " -> ";
		}
	}
	void deletePtr(NodLista* n) {
		if (n == nullptr) {
			return;
		}
		deletePtr(n->urm);
		delete n;
	}

	~Lista() {
		deletePtr(prim);
	}
};

class Stiva {
	Node* stiva[100];
	int top;
public:
	Stiva() {
		top = -1;
	};
	void adauga(Node* t) {
		top++;
		stiva[top] = t;
	}
	Node* scoate() {
		if (!goala()) {
			top--;
			return stiva[top + 1];
		}
		return nullptr;
	}

	bool goala() {
		if (top == -1) {
			return true;
		}
		return false;
	}
	~Stiva() {
		while (!goala()) {
			delete stiva[top];
			top--;
		}
	}
};