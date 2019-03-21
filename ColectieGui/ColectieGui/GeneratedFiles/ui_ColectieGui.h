/********************************************************************************
** Form generated from reading UI file 'ColectieGui.ui'
**
** Created by: Qt User Interface Compiler version 5.10.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_COLECTIEGUI_H
#define UI_COLECTIEGUI_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_ColectieGuiClass
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *ColectieGuiClass)
    {
        if (ColectieGuiClass->objectName().isEmpty())
            ColectieGuiClass->setObjectName(QStringLiteral("ColectieGuiClass"));
        ColectieGuiClass->resize(600, 400);
        menuBar = new QMenuBar(ColectieGuiClass);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        ColectieGuiClass->setMenuBar(menuBar);
        mainToolBar = new QToolBar(ColectieGuiClass);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        ColectieGuiClass->addToolBar(mainToolBar);
        centralWidget = new QWidget(ColectieGuiClass);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        ColectieGuiClass->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(ColectieGuiClass);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        ColectieGuiClass->setStatusBar(statusBar);

        retranslateUi(ColectieGuiClass);

        QMetaObject::connectSlotsByName(ColectieGuiClass);
    } // setupUi

    void retranslateUi(QMainWindow *ColectieGuiClass)
    {
        ColectieGuiClass->setWindowTitle(QApplication::translate("ColectieGuiClass", "ColectieGui", nullptr));
    } // retranslateUi

};

namespace Ui {
    class ColectieGuiClass: public Ui_ColectieGuiClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_COLECTIEGUI_H
