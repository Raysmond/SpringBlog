#!/usr/bin/python

import paramiko
import threading
import time

ip = '114.215.81.109'
user = 'root'
password = ''
dist = 'SpringBlog-production-0.1.war'
src_dir = '/root'
backup_dir = '/root/releases'
jetty_webapps = '/opt/jetty'

new_war=src_dir+'/'+dist
war=jetty_webapps+'/'+dist


def execute_cmds(ip, user, passwd, cmd):
    try:
        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(ip,22,user,passwd,timeout=5)
        for m in cmd:
            stdin, stdout, stderr = ssh.exec_command(m)
#           stdin.write("Y")
            out = stdout.readlines()
            for o in out:
                print o,
        print '%s\tOK\n'%(ip)
        ssh.close()
    except :
        print '%s\tError\n'%(ip)


if __name__=='__main__':
    print 'Start publishing %s to server %s'%(dist, ip)

    now = time.strftime("%Y%m%d%H%M%S")
    cmd = [
        'echo Stop jetty server... && service jetty stop',
        'echo Stop redis server... && service redis_6379 stop',
        'echo Flush all redis cache data... && redis-cli -r 1 flushall',
        'echo Copy ' + new_war + ' to ' + war + \
        ' && mv ' + war + ' ' + backup_dir + '/' + now + '-' + dist + \
        ' && cp ' + new_war + ' ' + war ,
        'service redis_6379 start',
        'service jetty start ' + \
        ' && echo All done.'
    ]
    a=threading.Thread(target=execute_cmds, args=(ip,user,password,cmd))
    a.start()
