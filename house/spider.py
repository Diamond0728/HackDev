import requests
from bs4 import BeautifulSoup
import csv
import time

i=14
j=334
f = open('data.csv','a+')
csv_write = csv.writer(f)
while i<=250:
    r = requests.get('https://sh.lianjia.com/chengjiao/pg'+str(i))
    soup = BeautifulSoup(r.text,'lxml')
    ul = soup.find('ul', attrs={'class': 'listContent'})
    if ul==None:
        break
    agroup = ul.find_all('a',attrs={'class':'img'})
    for a in agroup:
        # 初始化一个字典来存储文章信息
        h = requests.get(a['href'])
        smallsoup = BeautifulSoup(h.text,'lxml')
        total = smallsoup.find('span', attrs={'class':'dealTotalPrice'})
        msg = {}
        # 这里使用一个try except 防止爬虫找不到信息从而停止运行
        try:
            # 成交总价
            title = smallsoup.find('title')
            msg['title'] = title.text
            total = smallsoup.find('span', attrs={'class': 'dealTotalPrice'})
            total = total.find('i')
            msg['total'] = total.text
            #挂牌总价
            totalAtFirst = smallsoup.find('div', attrs={'class':'msg'})
            totalAtFirst = totalAtFirst.find('span').find('label')
            totalAtFirst = totalAtFirst.text
            #成交单价
            price = smallsoup.find('div', attrs={'class':'price'})
            price = price.find('b')
            msg['price'] = price.text
            info = smallsoup.find('div', attrs={'class':'newwrap baseinform'})
            #区
            location = smallsoup.find('div', attrs={'class': 'deal-bread'})
            a = location.find('a').find_next_sibling('a').find_next_sibling('a')
            msg['qu'] = a.text[:-5]
            #路
            a = a.find_next_sibling('a')
            msg['lu'] = a.text[:-5]
            # 小区
            a = a.find_next_sibling('a')
            msg['xiaoqu'] = a.text[:-5]
            #户型
            base = info.find('div', attrs={'class':'base'})
            li = base.find('li')
            msg['huxing'] = li.text[4:-1]
            #楼层
            li = li .find_next_sibling('li')
            msg['louceng'] = li.text[4:-1]
            #面积
            li = li .find_next_sibling('li')
            msg['mianji'] = li.text[4:-1]
            # 套内面积
            li = li.find_next_sibling('li')
            msg['taoneimianji'] = li.text[4:-1]
            # 建筑类型
            li = li.find_next_sibling('li').find_next_sibling('li')
            msg['jianzhuleixing'] = li.text[4:-1]
            # 房屋朝向
            li = li.find_next_sibling('li')
            msg['chaoxiang'] = li.text[4:-1]
            # 建成年代
            li = li.find_next_sibling('li')
            msg['jianchengniandai'] = li.text[4:-1]
            # 装修情况
            li = li.find_next_sibling('li')
            msg['zhuangxiuqingkuang'] = li.text[4:-1]
            # 建筑结构
            li = li.find_next_sibling('li')
            msg['jianzhujiegou'] = li.text[4:-1]
            # 供暖方式
            li = li.find_next_sibling('li')
            msg['gongnuanfangshi'] = li.text[4:-1]
            # 梯户比例
            li = li.find_next_sibling('li')
            msg['tihubili'] = li.text[4:-1]
            # 配备电梯
            li = li.find_next_sibling('li')
            msg['dianti'] = li.text[4:-1]
            
            
            transaction = info.find('div', attrs={'class': 'transaction'})
            li = transaction.find('li')
            # 交易属性
            li = li.find_next_sibling('li')
            msg['jiaoyishuxing'] = li.text[4:-1]
            # 房屋用途
            li = li.find_next_sibling('li').find_next_sibling('li')
            msg['fangwuyongtu'] = li.text[4:-1]
            # 产权年限
            li = li.find_next_sibling('li')
            msg['chanquannianxian'] = li.text[4:-1]

            result = []
            for key in msg:
                msg[key] = msg[key].replace(" ", "")
                result.append(msg[key])
                # msg[key] = msg[key].replace("", "")
            print(msg)
            if i==1 and j==1:
                csv_write.writerow(msg) #写入csv header
            csv_write.writerow(result)
            print ("第"+str(i) + "页")
            print ("第"+str(j)+"条数据已插入数据库")
            time.sleep(3)
            j = j+1
        except BaseException as e:
            print (e)
            print('出了点小问题')
    i=i+1
