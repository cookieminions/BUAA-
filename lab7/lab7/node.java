class node
{
	node(node next)
	{
		mx = next.mx;
		my = next.my;
		bx = next.bx;
		by = next.by;
		step = next.step;
		prev = next.prev;
	}
	
	node(int val_mx, int val_my, int val_bx, int val_by, int val_step,node val_prev)
	{
		mx = val_mx;
		my = val_my;
		bx = val_bx;
		by = val_by;
		step = val_step;
		prev = val_prev;
	}

	int mx, my, bx, by, step;
	node prev ; //��¼ǰ���ڵ㣬�Ա��ӡ�����˶��켣
}