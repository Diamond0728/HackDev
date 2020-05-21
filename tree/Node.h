//Node.h

#ifndef NODE_H
#define NODE_H
#include <iostream>
using namespace std;

//孩子结点
template <class ElemType>
struct Child{
    int data;                //数据域
    Child<ElemType> *next;    //指向下一个孩子的指针
    Child();        //无参数的构造函数
    Child(int n=0,Child<ElemType> *nex=NULL);
};

//数组中的结点
template <class ElemType>
struct ParentChild
{
    ElemType data;                //    数据域
    int parent;                    //双亲结点在数组中的位置
    Child<ElemType> *firstChild;                    //指向首孩子的指针
    
    ParentChild();                //无参数的构造函数
    ParentChild(ElemType val, int par=0, Child<ElemType> *fChild=NULL);    //有参数的构造函数
};



template <class ElemType>
ParentChild<ElemType>::ParentChild(){
    firstChild=NULL;
}

template <class ElemType>
ParentChild<ElemType>::ParentChild(ElemType val,int par,Child<ElemType> *fChild){
    data=val;
    parent=par;
    firstChild=fChild;
}

template <class ElemType>
Child<ElemType>::Child(){
    next=NULL;
}

template <class ElemType>
Child<ElemType>::Child(int n,Child<ElemType> *nex)
{
    data=n;
    next=nex;
}

#endif
