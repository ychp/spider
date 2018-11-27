var locks = [];

var LOCKTIME_DEFAULT = 1000 * 10;

function jsynchronized(lockName,lockTime){

    if(getLock(lockName)){

        return false;

    }else{

        setLock(lockName,true);

        setTimeout(function(){

            setLock(lockName,false);

        },lockTime?lockTime:LOCKTIME_DEFAULT);

        return true;

    }

}

/**

 * 获得一个锁，如果没有添加这个锁

 */

function getLock(lockName){

    for(var i = 0 ; i < locks.length ; i ++){

        if(locks[i][0] == lockName){

            return locks[i][1];

        }

    }

    locks[locks.length] = [lockName,false];

    return false;

}

/**

 * 设置一个锁，如果没有添加这个锁

 */

function setLock(lockName,lockValue){

    for(var i = 0 ; i < locks.length ; i ++){

        if(locks[i][0] == lockName){

            locks[i][1] = lockValue;

            return ;

        }

    }

    locks[locks.length] = [lockName,lockValue];

}

