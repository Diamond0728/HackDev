//ParentChildForest.h

#ifndef PARENTCHILDFOREST_H
#define PARENTCHILDFOREST_H
#include "Node.h"
#include <iostream>
using namespace std;



template<class ElemType>
class ParentChildForest
{
public:
    ParentChild<ElemType> a[50];        //存储空间
    ParentChild<ElemType> *root;
    int num;                    //总结点个数
public:
    ParentChildForest(ElemType item[],int parents[],int Num=0);        //有参数的构造函数
    void PreOrder(ParentChild<ElemType> r);            //先根遍历r为的子树森林
    void MidOrder(ParentChild<ElemType> r);            //中根遍历r为的子树森林
    void PostOrder(ParentChild<ElemType> r);        //后根遍历r为的子树森林
    int TreeNum();                //求森林中树的数目
    int Height();                //求森林的高度
    int Leaf();                    //叶子数
    void Show();
};

template<class ElemType>
ParentChildForest<ElemType>::ParentChildForest(ElemType item[],int parents[],int Num)
{
    int i;
    for(i=0;item[i]!=NULL;i++){            //全部存放到结点数组内
        a[i+1].data = item[i];
        a[i+1].parent = parents[i];
        a[i+1].firstChild = NULL;
    }
    a[0].parent=-1;
    num=Num;
    a[0].data=a[2].data;
    *root=a[0];
    for(i=1;i<=num;i++){            //构造各个结点的孩子的单链表
        Child<ElemType> *p,*q;
        q = new Child<ElemType>(i,NULL);
        if(a[a[i].parent].firstChild==NULL){
            a[a[i].parent].firstChild=q;
        }
        else{
            for(p=a[a[i].parent].firstChild;p->next!=NULL;p=p->next){}
            p->next=q;
        }
    }
    root->firstChild=a[0].firstChild;
}

template<class ElemType>
void ParentChildForest<ElemType>::PreOrder(ParentChild<ElemType> r)
{    //先根遍历
    if(r.firstChild!=NULL){        //若森林不为空
        cout<<a[r.firstChild->data].data<<"  ";        //访问第一棵树的根结点
        PreOrder(a[r.firstChild->data]);    //先根遍历第一棵树的根结点的子树森林
        if(r.firstChild->next!=NULL){
            ParentChild<ElemType> temp;
            temp.firstChild = r.firstChild->next;
            PreOrder(temp);                    //先根遍历其他树组成的森林
        }
    }
}

template<class ElemType>
void ParentChildForest<ElemType>::MidOrder(ParentChild<ElemType> r)
{    //中根遍历
    if(r.firstChild!=NULL){        //若森林不为空
        MidOrder(a[r.firstChild->data]);    //中根遍历第一棵树的根结点的子树森林
        cout<<a[r.firstChild->data].data<<"  ";        //访问第一棵树的根结点
        if(r.firstChild->next!=NULL){
            ParentChild<ElemType> temp;
            temp.firstChild = r.firstChild->next;
            MidOrder(temp);                    //中根遍历其他树组成的森林
        }
    }
}

template<class ElemType>
void ParentChildForest<ElemType>::PostOrder(ParentChild<ElemType> r)
{    //后根遍历
    if(r.firstChild!=NULL){        //若森林不为空
        PostOrder(a[r.firstChild->data]);    //后根遍历第一棵树的根结点的子树森林
        if(r.firstChild->next!=NULL){
            ParentChild<ElemType> temp;
            temp.firstChild = r.firstChild->next;
            PostOrder(temp);                    //后根遍历其他树组成的森林
        }
        cout<<a[r.firstChild->data].data<<"  ";        //访问第一棵树的根结点
    }
}

template<class ElemType>
int ParentChildForest<ElemType>::TreeNum()
{    //返回森林中树的数目

    int treenum=0;
    Child<ElemType> *p;
    p=a[0].firstChild;
    for(;p!=NULL;p=p->next){
        treenum++;
    }
    return treenum;

}

template<class ElemType>
int ParentChildForest<ElemType>::Height()
{    //求森林的高度
    if(a[0].firstChild!=NULL){
        int temp=0,height=0;
        Child<ElemType> *p;
        p=a[0].firstChild;
        for(;p->next!=NULL;p=p->next){
            temp=0;
            int i=p->next->data-1;
            while(a[i].parent>=0){
                i=a[i].parent;
                temp++;
            }
            height=(temp>height)?temp:height;
        }
        temp=0;
        int i=num;
        while(a[i].parent>=0){
            i=a[i].parent;
            temp++;
        }
        height=(temp>height)?temp:height;
        return height;
    }
    else{
        return 0;
    }
}

template<class ElemType>
int ParentChildForest<ElemType>::Leaf()
{
    int leaf=0;
    for(int i=0;i<=num;i++){
        if(a[i].firstChild==NULL){
            leaf++;
        }
    }
    return leaf;
}

template<class ElemType>
void ParentChildForest<ElemType>::Show()
{
    for(int i=0;i<=num;i++){
        cout<<i;
        cout<<"( "<<a[i].data<<", "<<a[i].parent<<", ";
        Child<ElemType> *p;
        if(a[i].firstChild==NULL){
            cout<<"^)"<<endl;
        }
        else{
            p=a[i].firstChild;
            while(p){
                cout<<" )->( "<<p->data<<", ";
                p=p->next;
            }
            cout<<"^)"<<endl;
        }
    }
}

#endif

