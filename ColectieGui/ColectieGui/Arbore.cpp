#include "Arbore.h"
#include <iostream>
/*
void adaugare_iterativ(Node* root, int value) {
Node *nod1, *nod2, *nod3 = new Node;
nod1->data = value;
nod1->right = NULL;
nod1->left = NULL;
nod1->parent = NULL;
if (root == NULL) {
root = nod1;
}
else {
nod2 = root;
while (nod2 != NULL) {
if (value < nod2->data) {
nod3 = nod2;
nod2 = nod2->left;
}
else {
nod3 = nod2;
nod2 = nod2->right;
}
}
if (value < nod3->data) {
nod1->parent = nod3;
nod3->left = nod1;
}
else {
nod1->parent = nod3;
nod3->right = nod1;
}
}
}
*/

Node* creeazaNod(const TElement& value, Node*parent) {
	Node* nod = new Node{ value,parent,NULL,NULL };
	return nod;
}
Node* adauga_rec(Node*root, Node* parent, TElement value) {
	if (root == NULL) {
		root = creeazaNod(value, parent);
	}
	else {
		if (value > root->data) {
			root->left = adauga_rec(root->left, root, value);
		}
		else {
			root->right = adauga_rec(root->right, root, value);
		}
	}
	return root;
}

void Arbore::adauga(Node* root, const TElement& value) {
	Arbore::root = adauga_rec(Arbore::root, NULL, value);

}

void Arbore::preordine(Node* root, std::vector<TElement>& preord) {
	if (root != NULL) {
		preord.push_back(root->data);
		preordine(root->left, preord);
		preordine(root->right, preord);
	}
}

void Arbore::inordine(Node* root, std::vector<TElement>& inord) {
	if (root != NULL) {
		inordine(root->left, inord);
		inord.push_back(root->data);
		inordine(root->right, inord);
	}
}

void Arbore::postordine(Node* root, std::vector<TElement>& postord) {
	if (root != NULL) {
		postordine(root->left, postord);
		postordine(root->right, postord);
		postord.push_back(root->data);
	}
}

Node* Arbore::sterge_rec(Node*p, const TElement& e) {
	Node* temp;
	Node* repl;
	if (p == nullptr) {
		return nullptr;
	}
	else {
		if (p->data > e) {
			p->left = sterge_rec(p->left, e);
			return p;
		}
		else {
			if (p->data < e) {
				p->right = sterge_rec(p->right, e);
				return p;
			}
			else {
				if (p->left != nullptr && p->right != nullptr) {
					temp = minim(p->right);
					p->data = temp->data;
					p->right = sterge_rec(p->right, p->data);
					return p;
				}
				else {
					temp = p;
					if (p->left == nullptr) {
						repl = p->right;
					}
					else {
						repl = p->left;
					}
					
					delete p;
					return repl;
				}
			}
		}
	}
}


void Arbore::sterge(const TElement& value) {
	Arbore::root = sterge_rec(root, value);
}

Node* Arbore::cauta(Node* root, const TElement& value) {
	if (root->data == value || root == NULL) {
		return root;
	}
	if (root->data < value) {
		return cauta(root->left, value);
	}
	else {
		return cauta(root->right, value);
	}
}

void Arbore::modifica(Node* root, const TElement& value) {
	if (root->data == value || root == NULL) {
		root->data=value;
	}
	else {
		if (root->data < value) {
			modifica(root->left, value);
		}
		else {
			modifica(root->right, value);
		}
	}
}

Node* Arbore::minim(Node* root) {
	while (root->left != NULL) {
		root = root->left;
	}
	return root;
}

Node* Arbore::maxim(Node* root) {
	while (root->right != NULL) {
		root = root->right;
	}
	return root;
}

