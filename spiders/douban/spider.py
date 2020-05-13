# -*- coding: UTF-8 -*-
import requests
import time
import json
import io
import re
from bs4 import BeautifulSoup

user_agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:68.0) Gecko/20100101 Firefox/68.0"

def getInfoByUrl(url):
    try:
        res = requests.get(url, headers={'User-Agent': user_agent})
        html = res.text
        soup = BeautifulSoup(html, 'lxml')
        span1 = soup.find('span', attrs={'property': 'v:summary'})
        span2 = soup.find('span', attrs={'class': 'hideen'})
        if span2 != None:
            return span2.text
        return span1.text
    except:
        return " "


i=0
url = 'https://movie.douban.com/j/new_search_subjects?'
types = ['爱情', '动作', '恐怖']
files = ['love', 'action', 'scary']
while i < len(types):
    start = 0
    while start < 400:
        params = {
            "start": start,
            "genres": types[i]
        }
        targetUrl = url + 'start=' + str(start) + "&genres=" + types[i]
        try:
            r = requests.get(targetUrl, headers={'User-Agent': user_agent})
        except:
            continue
        text = json.loads(r.text)
        movies = text['data']
        j = 0
        for movie in movies:
            j += 1
            info = getInfoByUrl(movie['url'])
            info = info.replace("\n", "")
            info = info.replace("  ", "")
            info = info.replace("　", "")
            print(i, start, j, info)
            with open(files[i] + '.txt', 'a+') as f:
                f.write(info + "\n")
            time.sleep(3)
            try:
                print(info)
            except:
                print("")
        start += 20
    i += 1

