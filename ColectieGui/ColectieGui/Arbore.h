#pragma once
#include "TElement.h"
#include <vector>

typedef Medicament TElement;
struct Node {
	TElement data;
	Node* parent = NULL;
	Node* left = NULL;
	Node* right = NULL;
};

class Arbore {
	Node* root = NULL;

public:

	Node * get_root()const {
		return root;
	}
	void set_root(TElement value) {
		root->data = value;
		root->left = root->right = NULL;
		root->parent = NULL;
	}
	Arbore() {};
	void adauga(Node* root, const TElement& value);

	void preordine(Node* root, std::vector<TElement>& preord);

	void inordine(Node* root, std::vector<TElement>& inord);

	void postordine(Node* root, std::vector<TElement>& postord);

	Node* cauta(Node* root, const TElement& value);

	void modifica(Node* root, const TElement& value);

	Node* sterge_rec(Node*p, const TElement& e);
	void sterge(const TElement& value);

	Node* minim(Node* root);

	Node* maxim(Node* root);

	void distruge(Node* root)
	{
		if (root!=nullptr)
		{
			distruge(root->left);
			distruge(root->right);
			delete root;
		}
	}
	~Arbore() {
		distruge(root);
	}
};
