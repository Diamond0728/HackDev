//main.cpp

#include "ParentChildForest.h"

int main(){
    char item[]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P'};
    int parents[]={0,1,1,0,4,5,0,7,7,7,8,8,10,10,10,13};
    ParentChildForest<char> t(item,parents,16);
    t.Show();
    cout<<"输入任意值以继续:";
    char c;
    cin>>c;
    cout<<"先根遍历: ";
    t.PreOrder(t.a[0]);
    cout<<endl;
    cout<<"中根遍历: ";
    t.MidOrder(t.a[0]);
    cout<<endl;
    cout<<"后根遍历: ";
    t.PostOrder(t.a[0]);
    cout<<endl;
    cout<<"该森林中树的数目为: "<<t.TreeNum()<<endl;
    cout<<"该森林的高度为: "<<t.Height()<<endl;
    cout<<"该森林的叶子数目为: "<<t.Leaf()<<endl;
    return 0;
}
